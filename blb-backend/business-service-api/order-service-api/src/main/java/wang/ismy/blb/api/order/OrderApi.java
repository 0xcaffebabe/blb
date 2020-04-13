package wang.ismy.blb.api.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.api.order.pojo.dto.OrderCreateDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderResultDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderValidCode;
import wang.ismy.blb.common.result.Result;

import java.math.BigDecimal;

/**
 * @author MY
 * @date 2020/4/9 9:08
 */
@Api(tags = "订单服务接口")
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
     * @return 出餐码、取餐码
     */
    @ApiOperation("获取订单验证码")
    @GetMapping("code")
    Result<OrderValidCode> getOrderValidCode();

    /**
     * 修改订单价格
     * @param orderId 订单号
     * @param amount 修改后的价格
     * @return 修改结果存在result的success与msg里
     */
    @ApiOperation("修改订单价格")
    @PutMapping("amount/{orderId}")
    Result<Void> updateOrderAmount(@PathVariable("orderId") Long orderId,
                                   @RequestParam("amount")BigDecimal amount);

}
