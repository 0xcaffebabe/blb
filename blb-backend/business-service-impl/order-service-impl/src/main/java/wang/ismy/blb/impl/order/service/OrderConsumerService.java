package wang.ismy.blb.impl.order.service;

import wang.ismy.blb.api.order.pojo.dto.OrderQuery;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderDetailDTO;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderItemDTO;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;

/**
 * @author MY
 * @date 2020/4/25 10:53
 */
public interface OrderConsumerService {
    /**
     * 获取买家订单列表
     * @param token
     * @param query
     * @param pageable
     * @return
     */
    Page<ConsumerOrderItemDTO> getOrderList(String token, OrderQuery query, Pageable pageable);

    /**
     * 买家订单详情
     *
     * @param token
     * @param orderId
     * @return
     */
    ConsumerOrderDetailDTO getOrderDetail(String token, Long orderId);
}
