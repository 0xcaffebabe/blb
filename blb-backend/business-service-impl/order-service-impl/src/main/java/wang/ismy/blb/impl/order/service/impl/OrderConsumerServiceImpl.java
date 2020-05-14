package wang.ismy.blb.impl.order.service.impl;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.order.pojo.dto.OrderDetailItemDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderQuery;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderDetailDTO;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderItemDTO;
import wang.ismy.blb.api.order.pojo.entity.OrderDO;
import wang.ismy.blb.common.BlbException;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.impl.order.client.AuthApiClient;
import wang.ismy.blb.impl.order.client.ShopApiClient;
import wang.ismy.blb.impl.order.repository.OrderDetailRepository;
import wang.ismy.blb.impl.order.repository.OrderRepository;
import wang.ismy.blb.impl.order.service.OrderConsumerService;

import java.math.RoundingMode;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/4/25 11:08
 */
@Service
@AllArgsConstructor
@Slf4j
@Setter
public class OrderConsumerServiceImpl implements OrderConsumerService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository detailRepository;
    private AuthApiClient authApiClient;
    private ShopApiClient shopApiClient;
    @Override
    public Page<ConsumerOrderItemDTO> getOrderList(String token, OrderQuery query, Pageable pageable) {
        var consumer = getConsumer(token);
        var dbPage = orderRepository
                .findAllByConsumerIdOrderByCreateTimeDesc(consumer.getUserId(), PageRequest.of(pageable.getPage().intValue()-1,pageable.getSize().intValue()));
        var shopIdList = dbPage.stream()
                .map(OrderDO::getShopId).collect(Collectors.toList());
        var shopRes = shopApiClient.getShopInfo(shopIdList);
        if (!shopRes.getSuccess()){
            log.warn("获取店铺信息失败:{}",shopRes);
            return new Page<>(
                    dbPage.getTotalElements(),
                    dbPage.stream()
                    .map(order->{
                        ConsumerOrderItemDTO dto = new ConsumerOrderDetailDTO();
                        BeanUtils.copyProperties(order,dto);
                        dto.setOrderAmount(order.getOrderAmount().setScale(2, RoundingMode.CEILING));
                        dto.setOrderName("订单");
                        return dto;
                    }).collect(Collectors.toList())
                    );
        }else {
            var shopMap = shopRes.getData();
            return new Page<>(
                    dbPage.getTotalElements(),
                    dbPage.stream()
                            .map(order->{
                                ConsumerOrderItemDTO dto = new ConsumerOrderDetailDTO();
                                BeanUtils.copyProperties(order,dto);
                                dto.setOrderAmount(order.getOrderAmount().setScale(2, RoundingMode.CEILING));
                                dto.setOrderName("订单");
                                dto.setShopLogo(shopMap.get(order.getShopId()).getShopLogo());
                                dto.setShopName(shopMap.get(order.getShopId()).getShopName());
                                dto.setShopId(order.getShopId());
                                return dto;
                            }).collect(Collectors.toList())
            );
        }
    }

    @Override
    public ConsumerOrderDetailDTO getOrderDetail(String token, Long orderId) {
        OrderDO orderDO = orderRepository.findById(orderId).orElseThrow(()->new BlbException("订单不存在"));
        var consumer = getConsumer(token);

        if (!orderDO.getConsumerId().equals(consumer.getUserId())){
            log.warn("订单{}不属于买家{}",orderDO.getOrderId(),consumer.getUserId());
            throw new BlbException("订单不属于买家");
        }
        var detailList= detailRepository.findAllByOrderId(orderDO.getOrderId());
        ConsumerOrderDetailDTO detailDTO = new ConsumerOrderDetailDTO();
        BeanUtils.copyProperties(orderDO,detailDTO);
        detailDTO.setProductList(
                detailList.stream().map(detail->{
                    OrderDetailItemDTO itemDTO = new OrderDetailItemDTO();
                    BeanUtils.copyProperties(detail,itemDTO);
                    return itemDTO;
                }).collect(Collectors.toList())
        );
        return detailDTO;
    }

    private User getConsumer(String token) {
        var authRes = authApiClient.valid(token);
        if (!authRes.getSuccess()){
            log.warn("获取消费者信息失败:{}",authRes);
            throw new BlbException("获取消费者失败");
        }
        return authRes.getData();
    }
}
