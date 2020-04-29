package wang.ismy.blb.impl.rider.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.order.enums.OrderStatusEnum;
import wang.ismy.blb.api.order.pojo.dto.OrderResultDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderValidCode;
import wang.ismy.blb.api.rider.pojo.dto.OrderRiderDTO;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.impl.rider.client.AuthApiClient;
import wang.ismy.blb.impl.rider.client.OrderApiClient;
import wang.ismy.blb.impl.rider.service.RedisService;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class RiderOrderServiceImplTest {

    @Autowired
    RiderOrderServiceImpl riderOrderService;

    @Test
    void getRiderByOrder() {
        Long orderId = 1L;
        OrderRiderDTO rider = riderOrderService.getRiderByOrder(orderId);
        assertEquals("蔡徐坤",rider.getRealName());
    }

    @Test
    void getRiderHistoryOrder() {
        String token = "token";
        Pageable pageable = Pageable.of(1L,5L);
        User user = new User();
        user.setUserId(1L);
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        riderOrderService.setAuthApiClient(authApiClient);

        var page = riderOrderService.getRiderHistoryOrder(token,pageable);
        assertEquals(2,page.getTotal());
        assertEquals(1L,page.getData().get(0).getOrderId());
        assertEquals(406L,page.getData().get(1).getOrderId());
    }

    @Test
    @Transactional
    void riderGrabOrder() {
        String token = "token";
        User user = new User();
        user.setUserId(1L);
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        riderOrderService.setAuthApiClient(authApiClient);

        OrderResultDTO order = new OrderResultDTO();
        order.setOrderId(3L);
        OrderApiClient orderApiClient = mock(OrderApiClient.class);
        when(orderApiClient.getOrder(eq(4L))).thenReturn(Result.success(order));
        riderOrderService.setOrderApiClient(orderApiClient);

        RedisService redisService = mock(RedisService.class);
        when(redisService.setIfNotExists(any(),anyString())).thenReturn(true);
        riderOrderService.setRedisService(redisService);

        String result = riderOrderService.riderGrabOrder(token,4L);
        assertEquals("接单成功，这是您的第3单",result);

        verify(orderApiClient).updateOrderStatus(eq(4L),eq(OrderStatusEnum.SHIPPING.getCode()));
    }

    @Test
    void riderCompleteOrder() {
        String token = "token";
        String code = "4321";
        User user = new User();
        user.setUserId(1L);
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        riderOrderService.setAuthApiClient(authApiClient);

        OrderResultDTO order = new OrderResultDTO();
        order.setOrderId(1L);
        OrderApiClient orderApiClient = mock(OrderApiClient.class);
        when(orderApiClient.getOrder(eq(1L))).thenReturn(Result.success(order));
        riderOrderService.setOrderApiClient(orderApiClient);

        OrderValidCode validCode = new OrderValidCode();
        validCode.setTakeMealCode("4321");
        when(orderApiClient.getOrderValidCode(eq(1L))).thenReturn(Result.success(validCode));

        String result = riderOrderService.riderCompleteOrder(token,1L,code);
        assertEquals("配送完结",result);
        verify(orderApiClient).updateOrderStatus(eq(1L),eq(OrderStatusEnum.DONE.getCode()));
    }
}