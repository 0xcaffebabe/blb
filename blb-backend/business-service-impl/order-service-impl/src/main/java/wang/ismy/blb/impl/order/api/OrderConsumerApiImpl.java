package wang.ismy.blb.impl.order.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.api.order.OrderConsumerApi;
import wang.ismy.blb.api.order.pojo.dto.OrderQuery;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderDetailDTO;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderItemDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.CurrentRequestUtils;
import wang.ismy.blb.impl.order.service.OrderConsumerService;

/**
 * @author MY
 * @date 2020/4/25 10:52
 */
@AllArgsConstructor
@RestController
public class OrderConsumerApiImpl implements OrderConsumerApi {
    private final OrderConsumerService consumerService;
    @Override
    public Result<Page<ConsumerOrderItemDTO>> getConsumerOrderList(OrderQuery query, Pageable pageable) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        return Result.success(consumerService.getOrderList(token,query,pageable));
    }

    @Override
    public Result<ConsumerOrderDetailDTO> getConsumerOrderDetail(Long orderId) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        return Result.success(consumerService.getOrderDetail(token,orderId));
    }
}
