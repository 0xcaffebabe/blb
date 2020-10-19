package wang.ismy.blb.aggregation.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.aggregation.client.PayApiClient;
import wang.ismy.blb.api.pay.enums.PayTypeEnum;
import wang.ismy.blb.api.pay.pojo.PayInfoDTO;
import wang.ismy.blb.api.pay.pojo.PayStatusDTO;
import wang.ismy.blb.common.result.Result;

/**
 * @author MY
 * @date 2020/5/9 13:42
 */
@RestController
@RequestMapping(value = "pay",produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "支付接口")
@AllArgsConstructor
public class PayAggApi {
    private final PayApiClient payApiClient;

    @ApiOperation("生成支付")
    @PostMapping("order/{orderId}")
    public Result<String> generatePay(@PathVariable Long orderId){
        return payApiClient.generatePay(orderId, PayTypeEnum.ALI_PAY.getCode());
    }

    @ApiOperation("获取支付二维码")
    @GetMapping("{payId}")
    public Result<PayInfoDTO> pay(@PathVariable Long payId){
        return payApiClient.pay(payId);
    }

    @ApiOperation("查询支付状态")
    @GetMapping("status/{payId}")
    public Result<PayStatusDTO> getPayStatus(@PathVariable Long payId){
        return payApiClient.getPayStatus(payId);
    }
}
