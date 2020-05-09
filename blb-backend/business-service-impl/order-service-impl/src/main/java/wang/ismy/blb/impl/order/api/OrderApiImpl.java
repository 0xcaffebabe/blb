package wang.ismy.blb.impl.order.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.api.order.OrderApi;
import wang.ismy.blb.api.order.pojo.dto.OrderCreateDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderResultDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderValidCode;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.CurrentRequestUtils;
import wang.ismy.blb.impl.order.service.OrderService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author MY
 * @date 2020/4/24 8:41
 */
@RestController
@AllArgsConstructor
public class OrderApiImpl implements OrderApi {
    private final OrderService orderService;
    @Override
    public Result<OrderResultDTO> getOrder(Long orderId) {
        return Result.success(orderService.getOrder(orderId));
    }

    @Override
    public Result<String> addOrder(@Valid OrderCreateDTO orderCreateDTO) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        return Result.success(orderService.addOrder(token,orderCreateDTO));
    }

    @Override
    public Result<Void> updateOrderStatus(Long orderId, Integer status) {
        orderService.updateOrderStatus(orderId,status);
        return Result.success();
    }

    @Override
    public Result<Void> updatePayStatus(Long orderId, Integer status) {
        orderService.updatePayStatus(orderId,status);
        return Result.success();
    }

    @Override
    public Result<OrderValidCode> getOrderValidCode(Long orderId) {
        return Result.success(orderService.getOrderValidCode(orderId));
    }

    @Override
    public Result<Void> updateOrderAmount(Long orderId, BigDecimal amount) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        orderService.updateOrderAmount(token,orderId,amount);
        return Result.success();
    }

    @Override
    public Result<Map<Long, Long>> getProductSales(List<Long> productIdList) {
        return Result.success(orderService.getProductSales(productIdList));
    }
}
