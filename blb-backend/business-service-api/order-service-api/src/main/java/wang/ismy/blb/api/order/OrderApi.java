package wang.ismy.blb.api.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.api.order.pojo.dto.OrderCreateDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderResultDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderValidCode;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.result.Result;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author MY
 * @date 2020/4/9 9:08
 */
@Api(tags = "订单服务接口")
@RequestMapping(value = "v1/api",produces = MediaType.APPLICATION_JSON_VALUE)
public interface OrderApi {

    /**
     * 根据订单ID查询订单
     * @param orderId
     * @return 订单展示DTO
     */
    @ApiOperation("根据订单ID查询订单")
    @ApiImplicitParam(type = "path",name = "orderId",dataType = "Long",required = true,value = "订单ID")
    @GetMapping("{orderId}")
    Result<OrderResultDTO> getOrder(@PathVariable("orderId") Long orderId);

    /**
     * 创建订单
     * @param orderCreateDTO 订单创建DTO
     * @return 订单ID
     */
    @ApiOperation("增加一条订单")
    @PostMapping("")
    Result<Long> addOrder(@RequestBody OrderCreateDTO orderCreateDTO);

    /**
     * 更新订单状态
     * @param orderId 订单ID
     * @param status 更新后的订单状态
     * @return 更新结果携带在result的success与msg里
     */
    @ApiOperation("更新订单状态")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "path",name = "orderId",dataType = "Long",required = true,value = "订单ID"),
            @ApiImplicitParam(type = "query",name = "status",dataType = "Integer",required = true,value = "更新后的订单状态")
    })
    @PutMapping("{orderId}")
    Result<Void> updateOrderStatus(@PathVariable("orderId") Long orderId,
                                   @RequestParam("status") Integer status);

    /**
     * 更新支付状态
     * @param orderId 订单ID
     * @param status 更新后的支付状态
     * @return 更新结果携带在result的success与msg里
     */
    @ApiOperation("更新订单支付状态")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "path",name = "orderId",dataType = "Long",required = true,value = "订单ID"),
            @ApiImplicitParam(type = "query",name = "status",dataType = "Integer",required = true,value = "更新后的订单支付状态")
    })
    @PutMapping("{orderId}/pay")
    Result<Void> updatePayStatus(@PathVariable("orderId") Long orderId,
                                   @RequestParam("status") Integer status);

    /**
     * 获取订单验证码
     * @param orderId
     * @return 出餐码、取餐码
     */
    @ApiOperation("获取订单验证码")
    @GetMapping("{orderId}/code")
    Result<OrderValidCode> getOrderValidCode(@PathVariable("orderId") Long orderId);

    /**
     * 修改订单价格
     * @param orderId 订单号
     * @param amount 修改后的价格
     * @return 修改结果存在result的success与msg里
     */
    @ApiOperation("修改订单价格")
    @PutMapping("{orderId}/amount")
    Result<Void> updateOrderAmount(@PathVariable("orderId") Long orderId,
                                   @RequestParam("amount")BigDecimal amount);

    /**
     * 根据商品ID列表批量计算商品销量
     * @param productIdList
     * @return 商品ID，商品销量 KV对
     */
    @GetMapping("sales/list")
    Result<Map<Long,Long>> getProductSales(@RequestParam("productIdList")List<Long> productIdList);

    class Fallback implements OrderApi {

        @Override
        public Result<OrderResultDTO> getOrder(Long orderId) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 订单服务 获取订单接口失败");
        }

        @Override
        public Result<Long> addOrder(OrderCreateDTO orderCreateDTO) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 订单服务 增加订单接口失败");
        }

        @Override
        public Result<Void> updateOrderStatus(Long orderId, Integer status) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 订单服务 更新订单状态接口失败");
        }

        @Override
        public Result<Void> updatePayStatus(Long orderId, Integer status) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 订单服务 更新支付状态接口失败");
        }

        @Override
        public Result<OrderValidCode> getOrderValidCode(Long orderId) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 订单服务 获取订单验证码接口失败");
        }

        @Override
        public Result<Void> updateOrderAmount(Long orderId, BigDecimal amount) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 订单服务 修改订单金额接口失败");
        }

        @Override
        public Result<Map<Long, Long>> getProductSales(List<Long> productIdList) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 订单服务 批量获取商品销量接口失败");
        }
    }
}
