package wang.ismy.blb.impl.pay.service.impl;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wang.ismy.blb.api.auth.UserTypeEnum;
import wang.ismy.blb.api.order.enums.PayStatusEnum;
import wang.ismy.blb.api.pay.enums.PayTypeEnum;
import wang.ismy.blb.api.pay.pojo.PayDO;
import wang.ismy.blb.api.pay.pojo.PayInfoDTO;
import wang.ismy.blb.api.pay.pojo.PayStatusDTO;
import wang.ismy.blb.common.BlbException;
import wang.ismy.blb.common.SnowFlake;
import wang.ismy.blb.impl.pay.client.AuthApiClient;
import wang.ismy.blb.impl.pay.client.OrderApiClient;
import wang.ismy.blb.impl.pay.client.ShopApiClient;
import wang.ismy.blb.impl.pay.pojo.PayResultDTO;
import wang.ismy.blb.impl.pay.repository.PayRepository;
import wang.ismy.blb.impl.pay.service.PayService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * @author MY
 * @date 2020/4/27 8:45
 */
@Service
@AllArgsConstructor
@Setter
@Slf4j
public class PayServiceImpl implements PayService {
    private final PayRepository payRepository;
    private AuthApiClient authApiClient;
    private OrderApiClient orderApiClient;
    private ShopApiClient shopApiClient;
    private final SnowFlake snowFlake;
    private AliPayService aliPayService;
    @Override
    public Long generatePay(String token, Long orderId, Integer type) {
        PayDO payDO = payRepository.findByOrderId(orderId);
        if (payDO != null){
            return payDO.getPayId();
        }
        var authRes = authApiClient.valid(token);
        if (!authRes.getSuccess()){
            log.warn("获取用户信息失败:{}",authRes);
            throw new BlbException("获取用户信息失败");
        }
        var consumer = authRes.getData();
        if (!consumer.getUserType().equals(UserTypeEnum.CONSUMER.getType())){
            log.warn("用户{}不是消费者",consumer.getUserId());
            throw new BlbException("不是消费者");
        }
        var orderRes = orderApiClient.getOrder(orderId);
        if (!orderRes.getSuccess()){
            log.warn("获取订单信息失败:{}",orderRes);
            throw new BlbException("获取订单信息失败");
        }
        var order = orderRes.getData();
        if (order == null){
            log.warn("订单{}不存在",orderId);
            throw new BlbException("订单不存在");
        }
        payDO = new PayDO();
        payDO.setConsumerId(consumer.getUserId());
        payDO.setOrderId(orderId);
        payDO.setPayTitle(consumer.getUsername()+"的点餐订单"+ LocalDateTime.now());
        payDO.setPayAmount(order.getOrderAmount());
        payDO.setPayId(snowFlake.nextId());
        payDO.setPayStatus(PayStatusEnum.UN_PROCESSED.getCode());
        payDO.setPayType(PayTypeEnum.valueOf(type).getType());
        payDO.initTime();
        payRepository.save(payDO);
        return payDO.getPayId();
    }

    @Override
    public PayInfoDTO pay(Long payId) {
        PayDO payDO = payRepository.findById(payId).orElseThrow(()->new BlbException("支付单不存在"));
        if (!payDO.getPayType().equals(PayTypeEnum.ALI_PAY.getType())){
            throw new BlbException("暂不支持此类型的支付");
        }
        String qrCode = aliPayService.generatePay(payDO);
        PayInfoDTO payInfo = new PayInfoDTO();
        payInfo.setUrl(qrCode);
        payInfo.setOrderId(payDO.getOrderId());
        var orderRes = orderApiClient.getOrder(payDO.getOrderId());
        if (!orderRes.getSuccess()){
            log.warn("获取订单 {} 信息失败:{}", payDO.getOrderId(), orderRes);
        }
        var shopRes = shopApiClient.getShopInfo(orderRes.getData().getShopId());
        if (!shopRes.getSuccess()){
            log.warn("获取订单信息时获取店铺信息失败：{}", shopRes);
        }else {
            payInfo.setShopName(shopRes.getData().getShopName());
        }
        return payInfo;
    }

    @Override
    public void callback(HttpServletRequest request, HttpServletResponse response) {
        // todo
    }

    @Override
    public void refund(String token, Long orderId) {
        //TODO
    }

    @Override
    public PayStatusDTO getPayStatus(Long payId) {
        PayDO payDO = payRepository.findById(payId).orElseThrow(()->new BlbException("支付单不存在"));
        PayResultDTO dto = aliPayService.getPay(payDO.getOrderId());
        PayStatusDTO status = new PayStatusDTO();
        status.setStatus(dto.getStatus());
        status.setMsg(dto.getMsg());
        if (status.getStatus().equals(PayStatusEnum.PROCESSED.getCode())){
            payDO.setPayStatus(status.getStatus());
            payRepository.save(payDO);
            orderApiClient.updatePayStatus(payDO.getOrderId(),status.getStatus());
        }
        return status;
    }
}
