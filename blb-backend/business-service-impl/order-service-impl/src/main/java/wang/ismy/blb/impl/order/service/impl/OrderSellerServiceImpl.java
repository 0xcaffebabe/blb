package wang.ismy.blb.impl.order.service.impl;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.order.enums.OrderStatusEnum;
import wang.ismy.blb.api.order.enums.PayStatusEnum;
import wang.ismy.blb.api.order.pojo.dto.NewOrderItemDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderDetailItemDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderQuery;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderDetailDTO;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderItemDTO;
import wang.ismy.blb.api.order.pojo.entity.OrderDO;
import wang.ismy.blb.api.order.pojo.entity.OrderDetailDO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.common.BlbException;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.impl.order.client.AuthApiClient;
import wang.ismy.blb.impl.order.client.ShopApiClient;
import wang.ismy.blb.impl.order.repository.OrderDetailRepository;
import wang.ismy.blb.impl.order.repository.OrderRepository;
import wang.ismy.blb.impl.order.service.OrderSellerService;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/4/25 10:07
 */
@Service
@AllArgsConstructor
@Slf4j
@Setter
public class OrderSellerServiceImpl implements OrderSellerService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository detailRepository;
    private AuthApiClient authApiClient;
    private ShopApiClient shopApiClient;
    @Override
    public Page<ConsumerOrderItemDTO> getSellerOrderList(String token, OrderQuery query, Pageable pageable) {
        User seller = getSeller(token);
        ShopInfoDTO shop = getShop(seller);

        var dbPage = orderRepository
                .findAllByShopId(shop.getShopId(), PageRequest.of(pageable.getPage().intValue()-1,pageable.getSize().intValue()));
        if (CollectionUtils.isEmpty(dbPage.getContent())){
            return new Page<>(dbPage.getTotalElements(),List.of());
        }
        var list = dbPage.stream().map(order->{
            ConsumerOrderItemDTO dto = new ConsumerOrderDetailDTO();
            BeanUtils.copyProperties(order,dto);
            dto.setOrderAmount(order.getOrderAmount().setScale(2, RoundingMode.CEILING));
            dto.setOrderName("订单");
            dto.setShopLogo(shop.getShopLogo());
            return dto;
        }).collect(Collectors.toList());
        return new Page<>(dbPage.getTotalElements(),list);
    }

    private ShopInfoDTO getShop(User seller) {
        var shopRes = shopApiClient.getShopBySeller(seller.getUserId());
        if (!shopRes.getSuccess()){
            log.warn("获取店铺失败:{}",shopRes);
            throw new BlbException("获取店铺失败");
        }
        return shopRes.getData();
    }

    private User getSeller(String token) {
        var authRes = authApiClient.valid(token);
        if (!authRes.getSuccess()){
            log.warn("获取商家信息失败:{}",authRes);
            throw new BlbException("获取商家失败");
        }
        return authRes.getData();
    }

    @Override
    public ConsumerOrderDetailDTO getSellerOrderDetail(String token, Long orderId) {
        OrderDO orderDO = orderRepository.findById(orderId).orElseThrow(()->new BlbException("订单不存在"));
        var seller = getSeller(token);
        var shop = getShop(seller);
        if (!orderDO.getShopId().equals(shop.getShopId())){
            log.warn("订单{}不属于卖家{}",orderDO.getOrderId(),seller.getUserId());
            throw new BlbException("订单不属于卖家");
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

    @Override
    public List<NewOrderItemDTO> getNewOrderList(String token) {
        var seller = getSeller(token);
        var shop = getShop(seller);

        var orderList = orderRepository.findNewOrder(OrderStatusEnum.UN_PROCESSED.getCode(),shop.getShopId(), PayStatusEnum.PROCESSED.getCode());
        var orderIdList = orderList.stream().map(OrderDO::getOrderId).collect(Collectors.toList());
        var orderDetailList = detailRepository.findAllByOrderIdIn(orderIdList);

        Map<Long,List<OrderDetailDO>> orderDetailMap = new HashMap<>(orderDetailList.size());
        for (OrderDetailDO orderDetailDO : orderDetailList) {
            orderDetailMap.computeIfAbsent(orderDetailDO.getOrderId(), k -> new ArrayList<>());
            orderDetailMap.get(orderDetailDO.getOrderId()).add(orderDetailDO);
        }

        List<NewOrderItemDTO> result = new ArrayList<>();
        for (OrderDO orderDO : orderList) {
            NewOrderItemDTO dto = new NewOrderItemDTO();
            BeanUtils.copyProperties(orderDO,dto);
            var detailList = orderDetailMap.get(orderDO.getOrderId());
            if (!CollectionUtils.isEmpty(detailList)){
                dto.setProductList(detailList.stream().map(detail->{
                    OrderDetailItemDTO itemDTO = new OrderDetailItemDTO();
                    BeanUtils.copyProperties(detail,itemDTO);
                    return itemDTO;
                }).collect(Collectors.toList()));
            }
            result.add(dto);
        }
        return result;
    }
}
