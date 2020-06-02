package wang.ismy.blb.api.rider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.api.rider.pojo.dto.OrderRiderDTO;
import wang.ismy.blb.api.rider.pojo.dto.order.RiderHistoryOrderItemDTO;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/8 14:12
 */
@Api(tags = "骑手订单服务接口")
@RequestMapping(value = "v1/api/order",produces = MediaType.APPLICATION_JSON_VALUE)
public interface RiderOrderApi {

    /**
     * 根据订单号取得骑手信息
     *
     * @param orderId
     * @return 订单展示骑手信息DTO
     */
    @GetMapping("{orderId}")
    @ApiImplicitParam(paramType = "path", name = "orderId", dataType = "Long", required = true, value = "订单ID")
    @ApiOperation("根据订单号取得骑手信息接口")
    Result<OrderRiderDTO> getRiderByOrder(@PathVariable("orderId") Long orderId);

    /**
     * 获取骑手未完成的订单
     * @return 未完成订单
     */
    @GetMapping("undelivery")
    Result<RiderHistoryOrderItemDTO> getRiderUnDeliveryOrder();

    /**
     * 获取骑手的历史订单
     *
     * @param pageable
     * @return 订单展示骑手信息DTO
     */
    @GetMapping("history")
    @ApiOperation("获取骑手的历史订单")
    Result<Page<RiderHistoryOrderItemDTO>> getRiderHistoryOrder(Pageable pageable);

    /**
     * 骑手接单接口
     * @param orderId
     * @return 接单成功/失败提示
     */
    @ApiOperation("骑手接单接口")
    @PostMapping("grab/{orderId}")
    Result<String> riderGrabOrder(@PathVariable("orderId") Long orderId);

    /**
     * 骑手完结订单接口
     * @param orderId
     * @param code 订餐者提供的验证码
     * @return 完结订单成功/失败提示
     */
    @ApiOperation("骑手完结订单接口")
    @PutMapping("complete/{orderId}")
    Result<String> riderCompleteOrder(@PathVariable("orderId") Long orderId,
                                      @RequestParam("code") String code);

    class Fallback implements RiderOrderApi{

        @Override
        public Result<OrderRiderDTO> getRiderByOrder(Long orderId) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 骑手订单服务 获取骑手接口失败");
        }

        @Override
        public Result<RiderHistoryOrderItemDTO> getRiderUnDeliveryOrder() {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 骑手订单服务 获取骑手未完成订单接口失败");
        }

        @Override
        public Result<Page<RiderHistoryOrderItemDTO>> getRiderHistoryOrder(Pageable pageable) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 骑手订单服务 获取骑手历史订单接口失败");
        }

        @Override
        public Result<String> riderGrabOrder(Long orderId) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 骑手订单服务 骑手接单接口失败");
        }

        @Override
        public Result<String> riderCompleteOrder(Long orderId, String code) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 骑手订单服务 骑手完结订单接口失败");
        }
    }
}
