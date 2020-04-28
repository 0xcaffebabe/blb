package wang.ismy.blb.impl.rider.service.impl;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.auth.UserTypeEnum;
import wang.ismy.blb.api.cache.CacheService;
import wang.ismy.blb.api.order.enums.OrderStatusEnum;
import wang.ismy.blb.api.order.pojo.dto.OrderResultDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalInfoDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalItemDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalResultDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalSubmitDTO;
import wang.ismy.blb.api.rider.pojo.entity.RiderEvaluationDO;
import wang.ismy.blb.api.rider.pojo.entity.RiderInfoDO;
import wang.ismy.blb.common.BlbException;
import wang.ismy.blb.common.SnowFlake;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.impl.rider.client.AuthApiClient;
import wang.ismy.blb.impl.rider.client.CacheApiClient;
import wang.ismy.blb.impl.rider.client.ConsumerApiClient;
import wang.ismy.blb.impl.rider.client.OrderApiClient;
import wang.ismy.blb.impl.rider.pojo.RiderOrderDO;
import wang.ismy.blb.impl.rider.repository.RiderEvaluationRepository;
import wang.ismy.blb.impl.rider.repository.RiderOrderRepository;
import wang.ismy.blb.impl.rider.service.RiderEvaluationService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/4/28 9:56
 */
@Service
@AllArgsConstructor
@Slf4j
@Setter
public class RiderEvaluationServiceImpl implements RiderEvaluationService {
    private final RiderEvaluationRepository evaluationRepository;
    private final RiderOrderRepository riderOrderRepository;
    private final SnowFlake snowFlake;
    private AuthApiClient authApiClient;
    private OrderApiClient orderApiClient;
    private CacheService cacheService;
    private ConsumerApiClient consumerApiClient;

    @Override
    public RiderEvalResultDTO addEvaluation(String token, RiderEvalSubmitDTO evalSubmitDTO) {
        User consumer = getConsumer(token);
        OrderResultDTO order = getOrder(evalSubmitDTO, consumer);

        RiderOrderDO riderOrderDO =
                riderOrderRepository
                        .findById(evalSubmitDTO.getOrderId()).orElseThrow(() -> new BlbException("该订单没有被骑手配送"));
        if (evaluationRepository.existsByOrderIdAndRiderId(order.getOrderId(), riderOrderDO.getRiderId())) {
            log.warn("订单已评价");
            return null;
        }
        RiderEvaluationDO evaluationDO = new RiderEvaluationDO();
        evaluationDO.setContent(evalSubmitDTO.getContent());
        evaluationDO.setOrderId(order.getOrderId());
        evaluationDO.setRanking(evalSubmitDTO.getRanking());
        evaluationDO.setRiderId(riderOrderDO.getRiderId());
        evaluationDO.initTime();
        evaluationDO.setConsumerId(consumer.getUserId());
        evaluationDO.setEvalId(snowFlake.nextId());
        evaluationRepository.save(evaluationDO);

        RiderEvalResultDTO result = new RiderEvalResultDTO();
        result.setEvalNumber(evaluationRepository.countByRiderId(riderOrderDO.getRiderId()));
        return result;
    }

    @Override
    public RiderEvalInfoDTO getRiderEvalInfo(Long riderId) {
        String key = "rider-eval-" + riderId;
        RiderEvalInfoDTO info = cacheService.get(key, RiderEvalInfoDTO.class);
        if (info != null) {
            return info;
        }
        var list = evaluationRepository.findByRiderId(riderId);
        BigDecimal total = BigDecimal.ZERO;
        for (RiderEvaluationDO evaluationDO : list) {
            total = total.add(evaluationDO.getRanking());
        }
        info = new RiderEvalInfoDTO();
        if (list.size() == 0) {
            info.setRanking(BigDecimal.ZERO);
            return info;
        }
        info.setRanking(total.divide(new BigDecimal(list.size()), RoundingMode.CEILING).setScale(2, RoundingMode.CEILING));
        return info;
    }

    @Override
    public Page<RiderEvalItemDTO> getRiderEvalList(Long riderId, Pageable pageable) {
        var dbPage = evaluationRepository.findByRiderId(riderId,
                PageRequest.of(pageable.getPage().intValue() - 1, pageable.getSize().intValue())
        );
        var consumerRes = consumerApiClient.getInfo(
                dbPage.stream()
                        .map(RiderEvaluationDO::getConsumerId)
                        .collect(Collectors.toList())
        );
        if (!consumerRes.getSuccess()) {
            log.warn("获取订餐者信息失败");
            return new Page<>(dbPage.getTotalElements(), dbPage.stream()
                    .map(eval -> {
                        RiderEvalItemDTO dto = new RiderEvalItemDTO();
                        BeanUtils.copyProperties(eval, dto);
                        return dto;
                    }).collect(Collectors.toList()));
        }
        var consumerMap = consumerRes.getData();
        return new Page<>(dbPage.getTotalElements(), dbPage.stream()
                .map(eval -> {
                    RiderEvalItemDTO dto = new RiderEvalItemDTO();
                    var consumer = consumerMap.get(eval.getConsumerId());
                    BeanUtils.copyProperties(eval, dto);
                    if (consumer != null) {
                        dto.setConsumer(consumer.getUsername());
                    }
                    return dto;
                }).collect(Collectors.toList()));

    }

    private OrderResultDTO getOrder(RiderEvalSubmitDTO evalSubmitDTO, User consumer) {
        var orderRes = orderApiClient.getOrder(evalSubmitDTO.getOrderId());
        if (!orderRes.getSuccess()) {
            log.warn("获取订单信息失败:{}", orderRes);
            throw new BlbException("获取订单信息失败");
        }
        var order = orderRes.getData();
        if (!order.getConsumerId().equals(consumer.getUserId())) {
            log.warn("订单{}不属于消费者{}", order.getOrderId(), consumer.getUserId());
            throw new BlbException("订单不属于消费者");
        }
        if (!order.getOrderStatus().equals(OrderStatusEnum.DONE.getCode())) {
            log.warn("订单状态非已完结，无法评价");
            throw new BlbException("订单状态错误");
        }
        return order;
    }

    private User getConsumer(String token) {
        var authRes = authApiClient.valid(token);
        if (!authRes.getSuccess()) {
            log.warn("获取用户信息失败:{}", authRes);
            throw new BlbException("获取用户信息失败");
        }
        var consumer = authRes.getData();
        if (!consumer.getUserType().equals(UserTypeEnum.CONSUMER.getType())) {
            log.warn("用户{}不是消费者", consumer.getUserId());
            throw new BlbException("不是消费者，无法评价");
        }
        return consumer;
    }
}
