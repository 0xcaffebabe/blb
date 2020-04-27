package wang.ismy.blb.impl.pay;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.api.pay.PayApi;
import wang.ismy.blb.api.pay.pojo.PayStatusDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.CurrentRequestUtils;
import wang.ismy.blb.impl.pay.service.PayService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author MY
 * @date 2020/4/26 8:44
 */
@RestController
@AllArgsConstructor
public class PayApiImpl implements PayApi {
    private final PayService payService;
    @Override
    public Result<String> generatePay(Long orderId, Integer type) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        return Result.success(payService.generatePay(token,orderId,type).toString());
    }

    @Override
    public Result<String> pay(Long payId) {
        return Result.success(payService.pay(payId));
    }

    @Override
    public Result<PayStatusDTO> getPayStatus(Long payId) {
        return Result.success(payService.getPayStatus(payId));
    }

    @Override
    public void callback(HttpServletRequest request, HttpServletResponse response) {
        payService.callback(request,response);
    }

    @Override
    public Result<Void> refund(Long orderId) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        payService.refund(token,orderId);
        return Result.success();
    }
}
