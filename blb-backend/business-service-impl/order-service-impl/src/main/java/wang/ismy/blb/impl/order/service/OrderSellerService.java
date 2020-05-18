package wang.ismy.blb.impl.order.service;

import wang.ismy.blb.api.order.pojo.dto.NewOrderItemDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderQuery;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderDetailDTO;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderItemDTO;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/25 9:17
 */
public interface OrderSellerService {
    /**
     * 获取卖家订单列表
     * @param token
     * @param query
     * @param pageable
     * @return
     */
    Page<ConsumerOrderItemDTO> getSellerOrderList(String token,OrderQuery query, Pageable pageable);

    /**
     * 卖家订单详情
     *
     * @param token
     * @param orderId
     * @return
     */
    ConsumerOrderDetailDTO getSellerOrderDetail(String token, Long orderId);

    /**
     * 获取卖家新订单列表
     * @param token
     * @return
     */
    List<NewOrderItemDTO> getNewOrderList(String token);
}
