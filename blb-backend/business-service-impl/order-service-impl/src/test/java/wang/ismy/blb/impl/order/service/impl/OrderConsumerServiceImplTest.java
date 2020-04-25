package wang.ismy.blb.impl.order.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.order.pojo.dto.OrderQuery;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.order.client.AuthApiClient;
import wang.ismy.blb.impl.order.client.ShopApiClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class OrderConsumerServiceImplTest {

    @Autowired
    OrderConsumerServiceImpl consumerService;

    @Test
    void getSellerOrderList() {
        String token = "token";
        OrderQuery query = MockUtils.create(OrderQuery.class);
        Pageable pageable = Pageable.of(1L,5L);
        User user = new User();
        user.setUserId(1L);
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        consumerService.setAuthApiClient(authApiClient);

        var shopMap = Map.of(1L,MockUtils.create(ShopInfoDTO.class),2L,MockUtils.create(ShopInfoDTO.class));
        ShopApiClient shopApiClient = mock(ShopApiClient.class);
        when(shopApiClient.getShopInfo(anyList())).thenReturn(Result.success(shopMap));
        consumerService.setShopApiClient(shopApiClient);

        var page = consumerService.getOrderList(token,query,pageable);
        assertEquals(shopMap.get(1L).getShopLogo(),page.getData().get(0).getShopLogo());
        assertEquals(shopMap.get(1L).getShopLogo(),page.getData().get(1).getShopLogo());

        assertEquals(new BigDecimal("7.00"),page.getData().get(0).getOrderAmount());
        assertEquals(new BigDecimal("15.00"),page.getData().get(1).getOrderAmount());
    }

    @Test
    void getSellerOrderDetail() {
        String token = "token";
        User user = new User();
        user.setUserId(1L);
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        consumerService.setAuthApiClient(authApiClient);

        var detail = consumerService.getOrderDetail(token,1L);
        assertEquals(new BigDecimal("7.0"),detail.getOrderAmount().setScale(1, RoundingMode.CEILING));
        assertEquals(2,detail.getProductList().size());

        assertEquals("黄焖鸡米饭",detail.getProductList().get(0).getProductName());
        assertEquals(1L,detail.getProductList().get(0).getProductSpec());
        assertEquals("黄焖鸡米饭",detail.getProductList().get(1).getProductName());
        assertEquals(2L,detail.getProductList().get(1).getProductSpec());
    }
}