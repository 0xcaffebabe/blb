package wang.ismy.blb.impl.rider.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.api.rider.RiderOrderApi;
import wang.ismy.blb.api.rider.pojo.dto.OrderRiderDTO;
import wang.ismy.blb.api.rider.pojo.dto.order.RiderHistoryOrderItemDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.CurrentRequestUtils;
import wang.ismy.blb.impl.rider.service.RiderOrderService;

/**
 * @author MY
 * @date 2020/4/29 8:35
 */
@RestController
@AllArgsConstructor
public class RiderOrderApiImpl implements RiderOrderApi {
    private final RiderOrderService orderService;
    @Override
    public Result<OrderRiderDTO> getRiderByOrder(Long orderId) {
        return Result.success(orderService.getRiderByOrder(orderId));
    }

    @Override
    public Result<Page<RiderHistoryOrderItemDTO>> getRiderHistoryOrder(Pageable pageable) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        return Result.success(orderService.getRiderHistoryOrder(token,pageable));
    }

    @Override
    public Result<String> riderGrabOrder(Long orderId) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        return Result.success(orderService.riderGrabOrder(token,orderId));
    }

    @Override
    public Result<String> riderCompleteOrder(Long orderId, String code) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        return Result.success(orderService.riderCompleteOrder(token,orderId,code));
    }
}
