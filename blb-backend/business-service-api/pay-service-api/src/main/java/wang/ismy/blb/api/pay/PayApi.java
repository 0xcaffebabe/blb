package wang.ismy.blb.api.pay;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.api.pay.pojo.PayInfoDTO;
import wang.ismy.blb.api.pay.pojo.PayStatusDTO;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.result.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author MY
 * @date 2020/4/9 11:12
 */
@Api(tags = "支付服务接口")
@RequestMapping(value = "v1/api",produces = MediaType.APPLICATION_JSON_VALUE)
public interface PayApi {

    /**
     * 生成支付
     * @param orderId
     * @param type
     * @return 支付ID
     */
    @ApiOperation("生成支付")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderId", dataType = "Long", required = true, value = "订单号"),
            @ApiImplicitParam(paramType = "query", name = "type", dataType = "String", required = true, value = "支付类型")
    })
    @PostMapping("order/{orderId}")
    Result<String> generatePay(@PathVariable("orderId") Long orderId,
                             @RequestParam("type") Integer type);

    /**
     * 获取支付URL
     * @param payId
     * @return 支付页面URL
     */
    @ApiOperation("根据支付ID获取支付信息")
    @ApiImplicitParam(paramType = "path", name = "payId", dataType = "Long", required = true, value = "支付ID")
    @GetMapping("/{payId}")
    Result<PayInfoDTO> pay(@PathVariable("payId") Long payId);

    /**
     * 获取支付单支付状态
     * @param payId
     * @return
     */
    @ApiOperation("获取支付单支付状态")
    @ApiImplicitParam(paramType = "path", name = "payId", dataType = "Long", required = true, value = "支付ID")
    @GetMapping("status/{payId}")
    Result<PayStatusDTO> getPayStatus(@PathVariable("payId") Long payId);

    /**
     * 第三方支付回调
     * @param request
     * @param response
     */
    @ApiOperation("第三方支付回调URL")
    @RequestMapping("callback")
    default void callback(HttpServletRequest request, HttpServletResponse response){}

    /**
     * 商家退款
     * @param orderId
     * @return 退款结果在success与msg里
     */
    @ApiOperation("退款接口")
    @ApiImplicitParam(paramType = "path", name = "orderId", dataType = "Long", required = true, value = "订单号")
    @PutMapping("refund/{orderId}")
    Result<Void> refund(@PathVariable("orderId") Long orderId);

    class Fallback implements PayApi {

        @Override
        public Result<String> generatePay(Long orderId, Integer type) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 支付服务 生成支付接口失败");
        }

        @Override
        public Result<PayInfoDTO> pay(Long payId) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 支付服务 支付接口失败");
        }

        @Override
        public Result<PayStatusDTO> getPayStatus(Long payId) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 支付服务 获取支付状态接口失败");
        }

        @Override
        public Result<Void> refund(Long orderId) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 支付服务 退款接口失败");
        }
    }
}
