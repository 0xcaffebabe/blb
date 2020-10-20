package wang.ismy.blb.impl.pay.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.auth.UserTypeEnum;
import wang.ismy.blb.api.order.pojo.dto.OrderResultDTO;
import wang.ismy.blb.api.order.pojo.entity.OrderDO;
import wang.ismy.blb.api.pay.pojo.PayDO;
import wang.ismy.blb.api.pay.pojo.PayInfoDTO;
import wang.ismy.blb.api.pay.pojo.PayStatusDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.impl.pay.client.AuthApiClient;
import wang.ismy.blb.impl.pay.client.OrderApiClient;
import wang.ismy.blb.impl.pay.client.ShopApiClient;
import wang.ismy.blb.impl.pay.pojo.PayResultDTO;
import wang.ismy.blb.impl.pay.repository.PayRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class PayServiceImplTest {

    @Autowired
    PayServiceImpl payService;

    @Autowired
    PayRepository payRepository;

    @Test
    @Transactional
    void generatePay() {
        Long orderId = 10L;
        String token = "token";
        Integer type = 0;

        User user = new User();
        user.setUserId(1L);
        user.setUserType(UserTypeEnum.CONSUMER.getType());
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        payService.setAuthApiClient(authApiClient);

        OrderResultDTO orderDO = new OrderResultDTO();
        orderDO.setOrderId(10L);
        orderDO.setOrderAmount(new BigDecimal("100"));
        OrderApiClient orderApiClient = mock(OrderApiClient.class);
        when(orderApiClient.getOrder(eq(orderId))).thenReturn(Result.success(orderDO));
        payService.setOrderApiClient(orderApiClient);

        Long payId = payService.generatePay(token, orderId, type);
        PayDO payDO = payRepository.findById(payId).orElseThrow();

        assertEquals(10L, payDO.getOrderId());
        assertEquals(1L, payDO.getConsumerId());
        assertEquals(new BigDecimal("100.00").setScale(2, RoundingMode.CEILING), payDO.getPayAmount().setScale(2, RoundingMode.CEILING));

    }

    @Test
    void pay() {
        Long payId = 1L;
        AliPayService aliPayService = mock(AliPayService.class);
        ShopApiClient shopApiClient = mock(ShopApiClient.class);
        OrderApiClient orderApiClient = mock(OrderApiClient.class);
        when(aliPayService.generatePay(any())).thenReturn("qr code");
        OrderResultDTO order = new OrderResultDTO();
        order.setShopId(1L);
        order.setOrderDetailList(new ArrayList<>());
        when(orderApiClient.getOrder(eq(1L))).thenReturn(Result.success(order));
        ShopInfoDTO shop = new ShopInfoDTO();
        shop.setShopName("黄焖鸡米饭");
        when(shopApiClient.getShopInfo(eq(1L))).thenReturn(Result.success(shop));
        payService.setAliPayService(aliPayService);
        payService.setShopApiClient(shopApiClient);
        payService.setOrderApiClient(orderApiClient);
        PayInfoDTO payInfo = payService.pay(payId);
        assertEquals("黄焖鸡米饭", payInfo.getShopName());
        assertEquals("qr code", payInfo.getUrl());
        assertEquals(1L, payInfo.getOrderId());
    }

    @Test
    void callback() {
    }

    @Test
    void refund() {
    }

    @Test
    void getPayStatus() {
        AliPayService aliPayService = mock(AliPayService.class);
        payService.setAliPayService(aliPayService);
        PayResultDTO payResultDTO = new PayResultDTO();
        payResultDTO.setMsg("支付成功");
        payResultDTO.setStatus(2);

        when(aliPayService.getPay(eq(1L))).thenReturn(payResultDTO);

        PayStatusDTO status = payService.getPayStatus(1L);
        assertEquals("支付成功",status.getMsg());
    }
}