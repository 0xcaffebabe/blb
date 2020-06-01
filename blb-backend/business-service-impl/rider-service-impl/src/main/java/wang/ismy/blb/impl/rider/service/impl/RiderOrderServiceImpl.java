package wang.ismy.blb.impl.rider.service.impl;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.order.enums.OrderStatusEnum;
import wang.ismy.blb.api.rider.pojo.dto.OrderRiderDTO;
import wang.ismy.blb.api.rider.pojo.dto.order.RiderHistoryOrderItemDTO;
import wang.ismy.blb.api.rider.pojo.entity.RiderDO;
import wang.ismy.blb.api.rider.pojo.entity.RiderInfoDO;
import wang.ismy.blb.common.BlbException;
import wang.ismy.blb.common.enums.ServiceName;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.impl.rider.client.AuthApiClient;
import wang.ismy.blb.impl.rider.client.OrderApiClient;
import wang.ismy.blb.impl.rider.client.ShopApiClient;
import wang.ismy.blb.impl.rider.pojo.RiderOrderDO;
import wang.ismy.blb.impl.rider.repository.RiderInfoRepository;
import wang.ismy.blb.impl.rider.repository.RiderOrderRepository;
import wang.ismy.blb.impl.rider.repository.RiderRepository;
import wang.ismy.blb.impl.rider.service.RedisService;
import wang.ismy.blb.impl.rider.service.RiderOrderService;

import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/4/29 8:54
 */
@Service
@AllArgsConstructor
@Setter
@Slf4j
public class RiderOrderServiceImpl implements RiderOrderService {
    private final RiderRepository riderRepository;
    private final RiderOrderRepository orderRepository;
    private final RiderInfoRepository infoRepository;
    private RedisService redisService;
    private AuthApiClient authApiClient;
    private OrderApiClient orderApiClient;
    private ShopApiClient shopApiClient;

    @Override
    public OrderRiderDTO getRiderByOrder(Long orderId) {
        RiderOrderDO riderOrderDO =
                orderRepository.findByOrderId(orderId).orElseThrow(() -> new BlbException("没有找到此订单对应的骑手"));
        Long riderId = riderOrderDO.getRiderId();
        RiderDO riderDO = riderRepository.findById(riderId).orElseThrow();
        RiderInfoDO riderInfoDO = infoRepository.findById(riderId).orElseThrow();

        OrderRiderDTO dto = new OrderRiderDTO();
        BeanUtils.copyProperties(riderDO,dto);
        BeanUtils.copyProperties(riderInfoDO,dto);
        return dto;
    }

    @Override
    public Page<RiderHistoryOrderItemDTO> getRiderHistoryOrder(String token, Pageable pageable) {
        User rider = getRider(token);
        var dbPage = orderRepository.findAllByRiderId(rider.getUserId(),
                PageRequest.of(pageable.getPage().intValue()-1,pageable.getSize().intValue()));
        // TODO 获取订单以及店铺信息

        return new Page<>(
                dbPage.getTotalElements(),
                dbPage.stream()
                .map(riderOrder->{
                    RiderHistoryOrderItemDTO dto = new RiderHistoryOrderItemDTO();
                    BeanUtils.copyProperties(riderOrder,dto);
                    return dto;
                }).collect(Collectors.toList())
                );
    }

    @Override
    public String riderGrabOrder(String token, Long orderId) {
        User rider = getRider(token);
        validOrderExists(orderId);
        if (orderRepository.existsByOrderId(orderId)){
            log.warn("订单{}已被接单",orderId);
            throw new BlbException("订单已被其他骑手接单");
        }

        String key = ServiceName.RIDER_SERVICE+"-order-grab-lock-"+orderId;
        if (!redisService.setIfNotExists(key,rider.getUserId().toString())){
            log.warn("订单已被其他骑手接单");
            throw new BlbException("订单已被其他骑手接单");
        }

        RiderOrderDO riderOrderDO = new RiderOrderDO();
        riderOrderDO.setOrderId(orderId);
        riderOrderDO.setRiderId(rider.getUserId());
        riderOrderDO.initTime();
        orderRepository.save(riderOrderDO);

        // 更新订单系统里的订单状态
        orderApiClient.updateOrderStatus(orderId, OrderStatusEnum.SHIPPING.getCode());

        return "接单成功，这是您的第"+orderRepository.countByRiderId(rider.getUserId())+"单";
    }

    private void validOrderExists(Long orderId) {
        var orderRes = orderApiClient.getOrder(orderId);
        if (!orderRes.getSuccess()){
            log.warn("获取订单失败:{}",orderRes);
            throw new BlbException("获取订单信息失败");
        }
        var order=orderRes.getData();
        if (order == null){
            log.warn("订单不存在:{}",orderId);
            throw new BlbException("订单不存在");
        }
    }

    private User getRider(String token) {
        var authRes = authApiClient.valid(token);
        if (!authRes.getSuccess()) {
            log.warn("获取骑手信息失败:{}", authRes);
            throw new BlbException("获取骑手信息失败");
        }
        return authRes.getData();
    }

    @Override
    public String riderCompleteOrder(String token, Long orderId, String code) {
        var rider = getRider(token);
        validOrderExists(orderId);
        var orderRes = orderApiClient.getOrderValidCode(orderId);
        if (!orderRes.getSuccess()){
            log.warn("获取订单验证码失败:{}",orderRes);
            throw new BlbException("获取订单验证码失败");
        }
        var validCode = orderRes.getData();
        if (!code.equals(validCode.getTakeMealCode())){
            log.warn("骑手提供的取餐码不正确:{}",code);
            throw new BlbException("提供的取餐码不正确");
        }
        // 更新订单系统里的订单状态
        orderApiClient.updateOrderStatus(orderId,OrderStatusEnum.DONE.getCode());
        return "配送完结";
    }

    @Override
    public RiderHistoryOrderItemDTO getRiderUnDeliveryOrder(String token) {
        var authRes = authApiClient.valid(token);
        if (!authRes.getSuccess()){
            log.warn("调用认证服务失败:{}",authRes);
            throw new BlbException("调用认证服务失败");
        }
        var rider = authRes.getData();
        var riderOrderDO = orderRepository.getLastOrder(rider.getUserId());

        var orderRes = orderApiClient.getOrder(riderOrderDO.getOrderId());
        if (!orderRes.getSuccess()){
            log.warn("调用订单服务失败:{}", orderRes);
            throw new BlbException("调用订单服务失败");
        }
        var order = orderRes.getData();
        if (order == null) {
            return null;
        }
        if (order.getOrderStatus().equals(OrderStatusEnum.SHIPPING.getCode())){
            RiderHistoryOrderItemDTO dto = new RiderHistoryOrderItemDTO();
            BeanUtils.copyProperties(order,dto);
            return dto;
        }
        return null;
    }
}
