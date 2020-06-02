package wang.ismy.blb.aggregation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.aggregation.client.*;
import wang.ismy.blb.aggregation.pojo.RiderOrderDTO;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.order.enums.OrderStatusEnum;
import wang.ismy.blb.api.order.pojo.dto.OrderResultDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderValidCode;
import wang.ismy.blb.api.rider.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.rider.pojo.dto.OrderRiderDTO;
import wang.ismy.blb.api.rider.pojo.dto.RegisterDTO;
import wang.ismy.blb.api.rider.pojo.dto.order.RiderHistoryOrderItemDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.JsonUtils;
import wang.ismy.blb.common.util.MockUtils;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RiderAggApiTest {

    MockMvc mockMvc;
    RiderApiClient riderApiClient;
    RiderOrderApiClient riderOrderApiClient;
    OrderApiClient orderApiClient;
    ShopApiClient shopApiClient;
    AuthApiClient authApiClient;

    @BeforeEach
    void setup(){
        riderApiClient = mock(RiderApiClient.class);
        riderOrderApiClient = mock(RiderOrderApiClient.class);
        orderApiClient = mock(OrderApiClient.class);
        shopApiClient = mock(ShopApiClient.class);
        authApiClient = mock(AuthApiClient.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new RiderAggApi(riderApiClient,
                riderOrderApiClient,
                orderApiClient,
                shopApiClient,
                authApiClient
                )).build();
    }

    @Test
    void register() throws Exception {
        var dto = MockUtils.create(RegisterDTO.class);
        String result = "result";
        when(riderApiClient.register(eq(dto))).thenReturn(Result.success(result));
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.parse(dto))
        )
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(result))));
    }

    @Test
    void login() throws Exception {
        String username = "Cxk";
        String password = "123";
        var dto = MockUtils.create(LoginResultDTO.class);
        when(riderApiClient.login(eq(username),eq(password))).thenReturn(Result.success(dto));
        mockMvc.perform(post("/login")
                .param("username",username)
                .param("password",password)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(dto))));
    }

    @Test void getUndelivery() throws Exception {
        var dto = MockUtils.create(RiderHistoryOrderItemDTO.class);
        when(riderOrderApiClient.getRiderUnDeliveryOrder()).thenReturn(Result.success(dto));

        mockMvc.perform(get("/order/undelivery")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(dto))));
    }

    @Test void getDelivery() throws Exception {
        var list = MockUtils.create(OrderResultDTO.class,10);
        when(orderApiClient.getDeliveryOrder()).thenReturn(Result.success(list));

        mockMvc.perform(get("/order/delivery")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(list))));
    }

    @Test void grabOrder() throws Exception{
        Long orderId = 1L;
        when(riderOrderApiClient.riderGrabOrder(eq(1L))).thenReturn(Result.success("success"));

        mockMvc.perform(post("/order/grab/"+orderId)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success("success"))));
    }

    @Test void getOrderDetail() throws Exception {
        Long orderId = 1L;
        OrderResultDTO order = MockUtils.create(OrderResultDTO.class);
        order.setOrderId(orderId);
        when(orderApiClient.getOrder(eq(orderId))).thenReturn(Result.success(order));

        when(riderOrderApiClient.getRiderByOrder(eq(order.getOrderId())))
                .thenReturn(Result.success(MockUtils.create(OrderRiderDTO.class)));

        ShopInfoDTO shop = MockUtils.create(ShopInfoDTO.class);
        when(shopApiClient.getShopInfo(eq(order.getShopId())))
                .thenReturn(Result.success(shop));

        OrderValidCode code = MockUtils.create(OrderValidCode.class);
        when(orderApiClient.getOrderValidCode(eq(order.getOrderId())))
                .thenReturn(Result.success(code));

        RiderOrderDTO result = new RiderOrderDTO();
        result.setOrderId(order.getOrderId());
        result.setTakeMealCode(code.getTakeMealCode());
        result.setDinnerOutCode(code.getDiningOutCode());
        result.setConsumerName(order.getConsumerName());
        result.setConsumerAddress(order.getConsumerAddress());
        result.setConsumerPhone(order.getConsumerPhone());
        result.setShopName(shop.getShopName());
        result.setShopAddress(shop.getShopAddress());

        mockMvc.perform(get("/order/detail/"+orderId))
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(result))));

    }

    @Test void setOrderDelivery () throws Exception {
        String token = "token";
        Long orderId = 1L;
        User user = new User();
        user.setUserId(1L);
        when(authApiClient.valid(eq(token)))
                .thenReturn(Result.success(user));

        OrderRiderDTO rider = new OrderRiderDTO();
        rider.setRiderId(1L);
        when(riderOrderApiClient.getRiderByOrder(eq(orderId)))
                .thenReturn(Result.success(rider));

        when(orderApiClient.updateOrderStatus(eq(orderId),eq(OrderStatusEnum.SHIPPING.getCode())))
                .thenReturn(Result.success());
        mockMvc.perform(post("/order/"+orderId+"/delivery")
            .header(SystemConstant.TOKEN,token)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success())));
    }

    @Test void setOrderComplete()throws Exception{
        Long orderId = 1L;
        String code = "1234";

        when(riderOrderApiClient.riderCompleteOrder(eq(orderId),eq(code))).thenReturn(Result.success("success"));

        mockMvc.perform(post("/order/"+orderId+"/complete")
                .param("code",code)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success("success"))));
    }
}