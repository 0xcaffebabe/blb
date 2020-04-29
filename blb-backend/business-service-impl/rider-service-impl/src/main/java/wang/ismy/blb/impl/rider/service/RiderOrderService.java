package wang.ismy.blb.impl.rider.service;

import wang.ismy.blb.api.rider.pojo.dto.OrderRiderDTO;
import wang.ismy.blb.api.rider.pojo.dto.order.RiderHistoryOrderItemDTO;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;

/**
 * @author MY
 * @date 2020/4/29 8:36
 */
public interface RiderOrderService {
    OrderRiderDTO getRiderByOrder(Long orderId);

    Page<RiderHistoryOrderItemDTO> getRiderHistoryOrder(String token, Pageable pageable);

    String riderGrabOrder(String token, Long orderId);

    String riderCompleteOrder(String token, Long orderId, String code);
}
