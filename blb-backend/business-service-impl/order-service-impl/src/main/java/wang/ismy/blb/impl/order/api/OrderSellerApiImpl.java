package wang.ismy.blb.impl.order.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.api.order.OrderSellerApi;
import wang.ismy.blb.api.order.pojo.dto.NewOrderItemDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderQuery;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderDetailDTO;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderItemDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.CurrentRequestUtils;
import wang.ismy.blb.impl.order.service.OrderSellerService;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/25 9:16
 */
@RestController
@AllArgsConstructor
public class OrderSellerApiImpl implements OrderSellerApi {
    private final OrderSellerService orderSellerService;

    @Override
    public Result<Page<ConsumerOrderItemDTO>> getSellerOrderList(OrderQuery query, Long page, Long size) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        return Result.success(orderSellerService.getSellerOrderList(token,query,Pageable.of(page,size)));
    }

    @Override
    public Result<List<NewOrderItemDTO>> getNewOrderList() {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        return Result.success(orderSellerService.getNewOrderList(token));
    }

    @Override
    public Result<ConsumerOrderDetailDTO> getSellerOrderDetail(Long orderId) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        return Result.success(orderSellerService.getSellerOrderDetail(token,orderId));
    }
}
