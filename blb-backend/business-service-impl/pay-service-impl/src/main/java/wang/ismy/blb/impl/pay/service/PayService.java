package wang.ismy.blb.impl.pay.service;

import wang.ismy.blb.api.pay.pojo.PayStatusDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author MY
 * @date 2020/4/26 8:45
 */
public interface PayService {
    /**
     * 生成支付单
     * @param token
     * @param orderId
     * @param type
     * @return
     */
    Long generatePay(String token, Long orderId, Integer type);

    /**
     * 支付
     * @param payId
     * @return 二维码或者跳转地址
     */
    String pay(Long payId);

    /**
     * 第三方支付机构回调接口
     * @param request
     * @param response
     */
    void callback(HttpServletRequest request, HttpServletResponse response);

    /**
     * 商家退款
     * @param token
     * @param orderId
     */
    void refund(String token, Long orderId);

    /**
     * 获取支付单支付状态
     * @param payId
     * @return
     */
    PayStatusDTO getPayStatus(Long payId);
}
