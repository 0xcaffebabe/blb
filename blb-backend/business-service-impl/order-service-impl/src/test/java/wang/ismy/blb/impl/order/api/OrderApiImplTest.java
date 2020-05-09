package wang.ismy.blb.impl.order.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.api.order.pojo.dto.OrderCreateDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderDetailCreateDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderResultDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderValidCode;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.order.service.OrderService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderApiImplTest {

    private MockMvc mockMvc;
    private OrderService orderService;

    @BeforeEach
    public void setUp() throws Exception {
        orderService = mock(OrderService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new OrderApiImpl(orderService)).build();
    }

    @Test
    void getOrder() throws Exception {
        Long orderId = 1L;
        OrderResultDTO dto = MockUtils.create(OrderResultDTO.class);
        when(orderService.getOrder(eq(orderId))).thenReturn(dto);

        mockMvc.perform(get("/v1/api/" + orderId))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(dto))));
    }

    @Test
    void addOrder() throws Exception {
        String token = "token";
        OrderCreateDTO orderCreateDTO = MockUtils.create(OrderCreateDTO.class);
        orderCreateDTO.setProductList(MockUtils.create(OrderDetailCreateDTO.class,5));
        when(orderService.addOrder(eq(token),eq(orderCreateDTO))).thenReturn("1");
        mockMvc.perform(post("/v1/api/")
                .contentType(MediaType.APPLICATION_JSON)
                .header(SystemConstant.TOKEN,token)
                .content(new ObjectMapper().writeValueAsBytes(orderCreateDTO))
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success("1"))));
    }

    @Test
    void updateOrderStatus() throws Exception {
        Long orderId = 1L;
        Integer orderStatus= 2;

        mockMvc.perform(put("/v1/api/" + orderId).param("status",orderStatus.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success())));
        verify(orderService).updateOrderStatus(eq(orderId),eq(orderStatus));
    }

    @Test
    void updatePayStatus() throws Exception {
        Long orderId = 1L;
        Integer payStatus= 2;

        mockMvc.perform(put("/v1/api/" + orderId+"/pay").param("status",payStatus.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success())));
        verify(orderService).updatePayStatus(eq(orderId),eq(payStatus));
    }

    @Test
    void getOrderValidCode() throws Exception {
        OrderValidCode code = MockUtils.create(OrderValidCode.class);
        Long orderId = 1L;
        when(orderService.getOrderValidCode(eq(orderId))).thenReturn(code);
        mockMvc.perform(get("/v1/api/" + orderId+"/code"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(code))));
    }

    @Test
    void updateOrderAmount() throws Exception {
        String token = "token";
        Long orderId = 1L;
        BigDecimal amount = new BigDecimal("3.5");
        mockMvc.perform(put("/v1/api/" + orderId+"/amount")
                .param("amount",amount.toPlainString())
                .header(SystemConstant.TOKEN,token)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success())));
        verify(orderService).updateOrderAmount(eq(token),eq(orderId),eq(amount));
    }

    @Test
    void getProductSales() throws Exception {
        var idList = List.of(1L,2L,3L);
        var map = Map.of(1L,10L,2L,20L,3L,30L);
        when(orderService.getProductSales(eq(idList))).thenReturn(map);

        mockMvc.perform(get("/v1/api/sales/list")
                .param("productIdList",idList.get(0).toString(),idList.get(1).toString(),idList.get(2).toString())

        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(map))));
    }
}