package wang.ismy.blb.impl.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wang.ismy.blb.api.order.enums.PayStatusEnum;
import wang.ismy.blb.api.order.pojo.dto.OrderResultDTO;
import wang.ismy.blb.api.pay.pojo.PayDO;
import wang.ismy.blb.common.BlbException;
import wang.ismy.blb.impl.pay.pojo.PayResultDTO;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author MY
 * @date 2020/4/27 9:45
 */
@Service
@Slf4j
public class AliPayService {
    @Value("${alipay.app-id}")
    private String appId;

    @Value("${alipay.private-key}")
    private String privateKey;

    @Value("${alipay.public-key}")
    private String publicKey;

    @Value("${alipay.gateway}")
    private String gateway;

    private AlipayClient alipayClient;

    @PostConstruct
    public void init() {
        alipayClient = new DefaultAlipayClient(gateway,
                appId, privateKey, "json", "utf-8", publicKey, "RSA2");
    }

    /**
     * 传入支付单，生成该支付单的二维码
     * @param
     * @return
     */
    public String generatePay(PayDO payDO){
        //创建API对应的request类
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizContent("{" +
                "    \"out_trade_no\":\""+payDO.getOrderId()+"\"," +//商户订单号
                "    \"total_amount\":\""+payDO.getPayAmount().setScale(2, RoundingMode.CEILING)+"\"," +
                "    \"subject\":\""+payDO.getPayTitle()+"\"," +
                "    \"store_id\":\"001\"," +
                "    \"timeout_express\":\"90m\"}");//订单允许的最晚付款时间
        AlipayTradePrecreateResponse response = null;
        try {
            response = alipayClient.execute(request);
            if (!response.isSuccess()){
                throw new BlbException("生成支付宝订单失败:"+response.getMsg());
            }
            return response.getQrCode();
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    public PayResultDTO getPay(Long orderId){
        //创建API对应的request类
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "    \"out_trade_no\":\""+orderId+"\"}");
        AlipayTradeQueryResponse response = null;
        PayResultDTO payResultDTO = new PayResultDTO();
        try {
            response = alipayClient.execute(request);
            if (!response.isSuccess()){
                payResultDTO.setStatus(-1);
                payResultDTO.setMsg("查询支付信息失败");
                return payResultDTO;
            }
            payResultDTO.setThirdPartId(response.getTradeNo());
            switch (response.getTradeStatus()){
                case "WAIT_BUYER_PAY":
                    payResultDTO.setStatus(PayStatusEnum.UN_PROCESSED.getCode());
                    payResultDTO.setMsg("等待用户支付");
                    break;
                case "TRADE_CLOSED":
                    payResultDTO.setStatus(PayStatusEnum.SHIPPING.getCode());
                    payResultDTO.setMsg("支付已关闭");
                    break;
                case "TRADE_SUCCESS":
                    payResultDTO.setStatus(PayStatusEnum.PROCESSED.getCode());
                    payResultDTO.setAmount(new BigDecimal(response.getTotalAmount()));
                    payResultDTO.setMsg("支付成功");
                    break;
                default:
                    break;
            }
            return payResultDTO;
        } catch (AlipayApiException e) {
            payResultDTO.setStatus(-1);
            payResultDTO.setMsg("查询支付信息失败");
            return payResultDTO;
        }
    }

    public void refund(Long orderId){
        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();//创建API对应的request类
        request.setBizContent("{" +
                "    \"out_trade_no\":\""+orderId+"\"}"); //设置业务参数
        try {
            AlipayTradeCancelResponse response = alipayClient.execute(request);//通过alipayClient调用API，获得对应的response类
            log.info("退款结果:{}",response.getMsg());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

    }
}
