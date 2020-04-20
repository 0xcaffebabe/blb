package wang.ismy.blb.impl.product.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.input.NullInputStream;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import wang.ismy.blb.api.auth.UserTypeEnum;
import wang.ismy.blb.api.cache.CacheService;
import wang.ismy.blb.api.order.enums.OrderStatusEnum;
import wang.ismy.blb.api.order.pojo.dto.OrderCreateDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderDetailItemDTO;
import wang.ismy.blb.api.product.pojo.ProductDO;
import wang.ismy.blb.api.product.pojo.ProductEvaluationDO;
import wang.ismy.blb.api.product.pojo.dto.eval.ConsumerEvalItem;
import wang.ismy.blb.api.product.pojo.dto.eval.EvalCreateDTO;
import wang.ismy.blb.api.product.pojo.dto.eval.ShopEvalInfo;
import wang.ismy.blb.common.SnowFlake;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.impl.product.ProductConstant;
import wang.ismy.blb.impl.product.client.AuthApiClient;
import wang.ismy.blb.impl.product.client.ConsumerApiClient;
import wang.ismy.blb.impl.product.client.OrderApiClient;
import wang.ismy.blb.impl.product.repository.ProductEvaluationRepository;
import wang.ismy.blb.impl.product.repository.ProductRepository;
import wang.ismy.blb.impl.product.service.ElasticsearchService;
import wang.ismy.blb.impl.product.service.ProductEvalService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/4/18 9:39
 */
@Service
@AllArgsConstructor
@Slf4j
public class ProductEvalServiceImpl implements ProductEvalService {
    private final ProductEvaluationRepository evaluationRepository;
    private final CacheService cacheService;
    private final ElasticsearchService elasticsearchService;
    private final ConsumerApiClient consumerApiClient;
    private final OrderApiClient orderApiClient;
    private final AuthApiClient authApiClient;
    private final ProductRepository productRepository;
    private final SnowFlake snowFlake;
    @Override
    public Map<Long, BigDecimal> getPositiveRating(List<Long> productIdList) {
        // TODO mock
        return productIdList.stream()
                .collect(Collectors.toMap(l -> l, l -> new BigDecimal(10L)));
    }

    @Override
    public BigDecimal getShopEval(Long shopId) {
        BigDecimal cacheScore = cacheService.get(getKey(shopId), BigDecimal.class);
        if (cacheScore != null) {
            return cacheScore;
        }
        var evalList = evaluationRepository.findAllByShopId(shopId);
        BigDecimal total = BigDecimal.ZERO;
        for (ProductEvaluationDO eval : evalList) {
            total = total.add(eval.getRanking());
        }
        total = total.divide(new BigDecimal(evalList.size()),RoundingMode.CEILING).setScale(1, RoundingMode.CEILING);

        // 写入缓存
        cacheService.put(getKey(shopId), total, ProductConstant.CACHE_TTL);
        return total;
    }

    @Override
    public ShopEvalInfo getShopEvalInfo(Long shopId) {
        String key = "shop-eval-info-" + shopId;
        ShopEvalInfo info = cacheService.get(key, ShopEvalInfo.class);
        if (info != null) {
            return info;
        }
        BigDecimal score = getShopEval(shopId);
        StringBuilder text = new StringBuilder();
        for (ProductEvaluationDO eval : evaluationRepository.findAllByShopId(shopId)) {
            text.append(eval.getContent());
        }
        List<String> wordCloud = elasticsearchService.getWordCloud(text.toString());
        info = new ShopEvalInfo();
        info.setRanking(score);
        info.setWordCloud(wordCloud);

        // 写入缓存
        cacheService.put(key, info, ProductConstant.CACHE_TTL);
        return info;
    }

    @Override
    public Page<ConsumerEvalItem> getShopEvalList(Long shopId, Pageable pageable) {
        long page = pageable.getPage();
        long size = pageable.getSize();
        var list = evaluationRepository.findAllByShopId(shopId, PageRequest.of((int) page - 1, (int) size));
        Page<ConsumerEvalItem> ret = new Page<>();
        ret.setTotal(list.getTotalElements());
        var consumerIdList = list.getContent().stream()
                .map(ProductEvaluationDO::getConsumerId)
                .collect(Collectors.toList());
        // 调用订餐者服务获取订餐者信息
        var consumerRes = consumerApiClient.getInfo(consumerIdList);
        List<ConsumerEvalItem> consumerEvalItems;
        if (!consumerRes.getSuccess()) {
            log.warn("商品评价 获取订餐者信息失败,{}", consumerRes);
            consumerEvalItems = list.getContent()
                    .stream()
                    .map(eval -> {
                        ConsumerEvalItem item = new ConsumerEvalItem();
                        BeanUtils.copyProperties(eval, item);
                        return item;
                    }).collect(Collectors.toList());
        } else {
            var consumerMap = consumerRes.getData();
            consumerEvalItems = list.getContent().stream().map(eval -> {
                var consumerInfo = consumerMap.get(eval.getConsumerId());
                ConsumerEvalItem item = new ConsumerEvalItem();
                BeanUtils.copyProperties(eval, item);
                if (consumerInfo != null) {
                    item.setNickName(consumerInfo.getUsername());
                }
                return item;
            }).collect(Collectors.toList());
        }
        return new Page<>(list.getTotalElements(), consumerEvalItems);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void createEval(String token, EvalCreateDTO evalCreateDTO) {
        var authRes = authApiClient.valid(token);
        if (!authRes.getSuccess()) {
            log.warn("创建商品评论 获取订餐者信息失败:{}", authRes);
            return;
        }
        var user = authRes.getData();
        if (user == null || !user.getUserType().equals(UserTypeEnum.CONSUMER.getType())) {
            log.warn("用户不存在或者用户不是订餐者");
            return;
        }
        var orderRes = orderApiClient.getOrder(evalCreateDTO.getOrderId());
        if (!orderRes.getSuccess()) {
            log.warn("创建商品评论 获取订单失败：{}", orderRes);
            return;
        }
        var order = orderRes.getData();
        if (!order.getConsumerId().equals(user.getUserId())) {
            log.warn("创建商品评论 订单({})不属于当前买家({})", evalCreateDTO.getOrderId(), user.getUserId());
            return;
        }
        if (!order.getOrderStatus().equals(OrderStatusEnum.DONE.getCode())){
            log.warn("订单状态非已完结，不能评论");
            return;
        }
        if (CollectionUtils.isEmpty(order.getOrderDetailList())) {
            log.warn("订单中没有商品");
            return;
        }
        int productSize = productRepository.findAllById(
                order.getOrderDetailList().stream()
                        .map(OrderDetailItemDTO::getProductId)
                        .collect(Collectors.toList())
        ).size();
        for (OrderDetailItemDTO orderDetail : order.getOrderDetailList()) {
            ProductEvaluationDO eval = new ProductEvaluationDO();
            eval.setConsumerId(user.getUserId());
            eval.setContent(evalCreateDTO.getContent());
            eval.setRanking(evalCreateDTO.getRanking());
            eval.setEvalId(snowFlake.nextId());
            eval.setProductId(orderDetail.getProductId());
            eval.setShopId(order.getShopId());
            eval.setCreateTime(LocalDateTime.now());
            eval.setUpdateTime(LocalDateTime.now());
            evaluationRepository.save(eval);
        }
    }

    private String getKey(Long shopId) {
        return "shop-eval-" + shopId;
    }
}
