package wang.ismy.blb.impl.order.service.impl;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.cache.CacheService;
import wang.ismy.blb.api.consumer.ConsumerDeliveryApi;
import wang.ismy.blb.api.consumer.pojo.dto.ConsumerDTO;
import wang.ismy.blb.api.consumer.pojo.dto.DeliveryDTO;
import wang.ismy.blb.api.order.enums.OrderStatusEnum;
import wang.ismy.blb.api.order.enums.PayStatusEnum;
import wang.ismy.blb.api.order.pojo.dto.OrderCreateDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderDetailItemDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderResultDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderValidCode;
import wang.ismy.blb.api.order.pojo.entity.OrderDO;
import wang.ismy.blb.api.order.pojo.entity.OrderDetailDO;
import wang.ismy.blb.api.product.pojo.dto.CartProductGetDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductSpecDTO;
import wang.ismy.blb.common.BlbException;
import wang.ismy.blb.common.SnowFlake;
import wang.ismy.blb.impl.order.OrderConstant;
import wang.ismy.blb.impl.order.client.*;
import wang.ismy.blb.impl.order.repository.OrderDetailRepository;
import wang.ismy.blb.impl.order.repository.OrderRepository;
import wang.ismy.blb.impl.order.service.OrderService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/4/24 9:24
 */
@Service
@AllArgsConstructor
@Setter
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private AuthApiClient authApiClient;
    private ProductApiClient productApiClient;
    private ConsumerApiClient consumerApiClient;
    private ConsumerDeliveryApiClient deliveryApiClient;
    private ShopApiClient shopApiClient;
    private CacheService cacheService;
    private final SnowFlake snowFlake;
    @Override
    public OrderResultDTO getOrder(Long orderId) {
        OrderDO orderDO = orderRepository.findById(orderId).orElseThrow(() -> new BlbException("订单不存在"));
        List<OrderDetailDO> detailList = orderDetailRepository.findAllByOrderId(orderDO.getOrderId());
        OrderResultDTO dto = new OrderResultDTO();
        BeanUtils.copyProperties(orderDO,dto);
        dto.setOrderDetailList(
                detailList.stream().map(detail->{
                    OrderDetailItemDTO detailItemDTO = new OrderDetailItemDTO();
                    BeanUtils.copyProperties(detail,detailItemDTO);
                    return detailItemDTO;
                }).collect(Collectors.toList())
        );
        return dto;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String addOrder(String token,OrderCreateDTO orderCreateDTO) {
        // 验证订餐者
        User consumer = getConsumer(token);
        // 根据商品与商品规格拉取 商品列表
        List<ProductDTO> productList = getProductList(orderCreateDTO);
        if (CollectionUtils.isEmpty(productList)){
            log.warn("获取不到商品列表");
            throw new BlbException("获取不到商品列表");
        }
        Long shopId = productList.get(0).getProductCategory().getShopId();
        var specQuantityMap = orderCreateDTO.getProductList()
                .stream()
                .collect(Collectors.toMap(dto->dto.getSpecId(),dto->dto.getQuantity()));
        // 获取用户信息
        ConsumerDTO consumerInfo = getConsumerInfo(consumer);
        // 获取用户收货信息
        DeliveryDTO delivery = getDelivery(orderCreateDTO);
        // 写入订单
        OrderDO orderDO = writeOrder(orderCreateDTO, consumer, productList, shopId, specQuantityMap, consumerInfo, delivery);
        // 写入订单详情
        for (ProductDTO productDTO : productList) {
            var spec = productDTO.getProductSpecList().get(0);
            var quantity = specQuantityMap.get(spec.getSpecId());
            writeOrderDetail(orderDO, productDTO, spec, quantity);
        }
        // 生成订单验证码并写入
        generateAndWriteValidCode(orderDO.getOrderId());
        // 向消息队列写入消息：订单 订单详情 TODO
        return orderDO.getOrderId().toString();
    }

    private void generateAndWriteValidCode(Long orderId) {
        OrderValidCode validCode = new OrderValidCode();
        validCode.setDiningOutCode(""+ RandomUtils.nextInt(0,9)+ RandomUtils.nextInt(0,9)+
                RandomUtils.nextInt(0,9)+ RandomUtils.nextInt(0,9));
        validCode.setTakeMealCode(""+ RandomUtils.nextInt(0,9)+ RandomUtils.nextInt(0,9)+
                RandomUtils.nextInt(0,9)+ RandomUtils.nextInt(0,9));
        cacheService.put(getValidCodeKey(orderId),validCode, OrderConstant.VALID_CODE_TTL);
    }

    private String getValidCodeKey(Long orderId){
        return "order-valid-code-"+orderId;
    }
    @Override
    public void updateOrderStatus(Long orderId, Integer status) {
        OrderDO orderDO = orderRepository.findById(orderId).orElseThrow(()->new BlbException("订单不存在"));
        if (status <= orderDO.getOrderStatus()){
            throw new BlbException("设置状态不得小于等于先前状态");
        }
        OrderStatusEnum orderStatus = OrderStatusEnum.valueOf(status);
        if (orderStatus == null){
            log.warn("订单状态不存在:{}",status);
            throw new BlbException("订单状态不存在");
        }
        orderDO.setOrderStatus(orderStatus.getCode());
        orderRepository.save(orderDO);
    }

    @Override
    public void updatePayStatus(Long orderId, Integer status) {
        OrderDO orderDO = orderRepository.findById(orderId).orElseThrow(()->new BlbException("订单不存在"));
        if (status <= orderDO.getOrderStatus()){
            throw new BlbException("设置状态不得小于等于先前状态");
        }
        PayStatusEnum payStatus = PayStatusEnum.valueOf(status);
        if (payStatus == null){
            log.warn("支付状态不存在:{}",status);
            throw new BlbException("支付状态不存在");
        }
        orderDO.setPayStatus(payStatus.getCode());
        orderRepository.save(orderDO);
    }

    @Override
    public OrderValidCode getOrderValidCode(Long orderId) {
        if (!orderRepository.existsById(orderId)){
            log.warn("订单不存在:{}",orderId);
            throw new BlbException("订单不存在");
        }
        return cacheService.get(getValidCodeKey(orderId),OrderValidCode.class);
    }

    @Override
    public void updateOrderAmount(String token, Long orderId, BigDecimal amount) {
        var authRes = authApiClient.valid(token);
        if (!authRes.getSuccess()){
            log.warn("获取卖家信息失败:{}",authRes);
            return;
        }
        var seller = authRes.getData();
        OrderDO orderDO = orderRepository
                .findById(orderId).orElseThrow(()->new BlbException("订单不存在"));
        var shopRes = shopApiClient.getShopInfo(orderDO.getShopId());
        if (!shopRes.getSuccess()){
            log.warn("获取店铺信息失败:{}",shopRes);
            return;
        }
        var shop = shopRes.getData();
        if (shop == null){
            log.warn("获取不到当前卖家的店铺信息");
            return;
        }
        if (!shop.getSellerId().equals(seller.getUserId())){
            log.warn("店铺{}不属于卖家{}",shop.getShopId(),seller.getUserId());
            return;
        }
        orderDO.setOrderAmount(amount);
        orderRepository.save(orderDO);
    }

    @Override
    public Map<Long, Long> getProductSales(List<Long> productIdList) {
        Map<Long,Long> map = new HashMap<>();
        for (OrderDetailDO orderDetailDO : orderDetailRepository.findAllByProductIdIn(productIdList)) {
            map.merge(orderDetailDO.getProductId(), orderDetailDO.getProductQuantity().longValue(), Long::sum);
        }
        return map;
    }

    private void writeOrderDetail(OrderDO orderDO, ProductDTO productDTO, ProductSpecDTO spec, Integer quantity) {
        OrderDetailDO orderDetailDO = new OrderDetailDO();
        orderDetailDO.setDetailId(snowFlake.nextId());
        orderDetailDO.setOrderId(orderDO.getOrderId());
        orderDetailDO.setProductId(productDTO.getProductId());
        orderDetailDO.setProductName(productDTO.getProductName());
        orderDetailDO.setProductImg(productDTO.getProductImg());
        orderDetailDO.setProductQuantity(quantity);
        orderDetailDO.setProductSpec(spec.getSpecId());
        orderDetailDO.setProductPrice(spec.getPrice().add(spec.getPackageFee()).multiply(new BigDecimal(quantity)));
        orderDetailDO.initTime();
        orderDetailRepository.save(orderDetailDO);
    }

    private OrderDO writeOrder(OrderCreateDTO orderCreateDTO, User consumer, List<ProductDTO> productList, Long shopId, Map<Long, Integer> specQuantityMap, ConsumerDTO consumerInfo, DeliveryDTO delivery) {
        OrderDO orderDO = new OrderDO();
        orderDO.setOrderId(snowFlake.nextId());
        orderDO.setShopId(shopId);
        orderDO.setConsumerId(consumer.getUserId());
        orderDO.setConsumerName(consumerInfo.getRealName());
        orderDO.setConsumerPhone(consumerInfo.getPhone());
        orderDO.setConsumerAddress(delivery.getBuilding()+" "+delivery.getDetail());

        orderDO.setOrderStatus(OrderStatusEnum.UN_PROCESSED.getCode());
        orderDO.setPayStatus(PayStatusEnum.UN_PROCESSED.getCode());
        orderDO.setOrderNote(orderCreateDTO.getOrderNote());
        orderDO.initTime();
        // 计算商品总价
        BigDecimal amount = getAmount(productList, specQuantityMap);
        orderDO.setOrderAmount(amount);
        orderRepository.save(orderDO);
        return orderDO;
    }

    private BigDecimal getAmount(List<ProductDTO> productList, Map<Long, Integer> specQuantityMap) {
        BigDecimal amount = BigDecimal.ZERO;
        for (ProductDTO productDTO : productList) {
            var spec = productDTO.getProductSpecList().get(0);
            var quantity = specQuantityMap.get(spec.getSpecId());
            var price = spec.getPrice().add(spec.getPackageFee()).multiply(new BigDecimal(quantity));
            amount = amount.add(price);
        }
        return amount;
    }

    private DeliveryDTO getDelivery(OrderCreateDTO orderCreateDTO) {
        var deliveryRes = deliveryApiClient.getDeliveryInfo(orderCreateDTO.getDeliveryId());
        if (!deliveryRes.getSuccess()){
            log.warn("获取收货信息失败:{}",deliveryRes);
            throw new BlbException("获取收货信息失败");
        }
        return deliveryRes.getData();
    }

    private ConsumerDTO getConsumerInfo(User consumer) {
        var consumerRes = consumerApiClient.getInfo(consumer.getUserId());
        if (!consumerRes.getSuccess()){
            log.warn("获取用户信息失败:{}",consumerRes);
            throw new BlbException("获取用户信息失败");
        }
        return consumerRes.getData();
    }

    private List<ProductDTO> getProductList(OrderCreateDTO orderCreateDTO) {
        var cartGetList = orderCreateDTO.getProductList().stream()
                .map(createDto->{
                    CartProductGetDTO dto = new CartProductGetDTO();
                    dto.setProductId(createDto.getProductId());
                    dto.setSpecId(createDto.getSpecId());
                    return dto;
                }).collect(Collectors.toList());
        var productRes = productApiClient.getListByProductAndSpecList(cartGetList);
        if (!productRes.getSuccess()){
            log.warn("调用商品服务接口失败:{}",productRes);
            throw new BlbException("获取商品信息失败");
        }
        return productRes.getData();
    }

    private User getConsumer(String token) {
        var authRes = authApiClient.valid(token);
        if (!authRes.getSuccess()){
            log.warn("获取消费者信息失败:{}",authRes);
            throw new BlbException("获取消费者信息失败");
        }
        return authRes.getData();
    }
}
