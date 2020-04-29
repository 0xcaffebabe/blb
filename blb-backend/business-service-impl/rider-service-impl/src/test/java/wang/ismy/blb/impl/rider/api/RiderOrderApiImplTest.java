package wang.ismy.blb.impl.rider.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.api.rider.pojo.dto.OrderRiderDTO;
import wang.ismy.blb.api.rider.pojo.dto.order.RiderHistoryOrderItemDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.rider.service.RiderOrderService;
import wang.ismy.blb.impl.rider.service.RiderService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RiderOrderApiImplTest {

    private MockMvc mockMvc;
    private RiderOrderService orderService;

    @BeforeEach
    public void setUp() throws Exception {
        orderService = mock(RiderOrderService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new RiderOrderApiImpl(orderService)).build();
    }

    @Test
    void getRiderByOrder() throws Exception {
        Long orderId = 1L;
        OrderRiderDTO orderRiderDTO = MockUtils.create(OrderRiderDTO.class);
        when(orderService.getRiderByOrder(eq(orderId))).thenReturn(orderRiderDTO);
        mockMvc.perform(get("/v1/api/order/"+orderId)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(orderRiderDTO))));
    }

    @Test
    void getRiderHistoryOrder() throws Exception {
        String token = "token";
        Pageable pageable = Pageable.of(1L,5L);
        var list = MockUtils.create(RiderHistoryOrderItemDTO.class,5);
        var page = new Page<>(5L,list);
        when(orderService.getRiderHistoryOrder(eq(token),eq(pageable))).thenReturn(page);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc.perform(get("/v1/api/order/history/")
                .header(SystemConstant.TOKEN,token)
                .param("page",pageable.getPage().toString())
                .param("size",pageable.getSize().toString())
        )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Result.success(page))));
    }

    @Test
    void riderGrabOrder() throws Exception {
        Long orderId = 1L;
        String result = "接单成功";
        String token = "token";
        when(orderService.riderGrabOrder(eq(token),eq(orderId))).thenReturn(result);

        mockMvc.perform(post("/v1/api/order/grab/"+orderId)
                .header(SystemConstant.TOKEN,token)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(result))));
    }

    @Test
    void riderCompleteOrder() throws Exception {
        Long orderId = 1L;
        String result = "完结订单成功";
        String code = "4321";
        String token = "token";
        when(orderService.riderCompleteOrder(eq(token),eq(orderId),eq(code))).thenReturn(result);

        mockMvc.perform(put("/v1/api/order/complete/"+orderId)
                .header(SystemConstant.TOKEN,token)
                .param("code",code)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(result))));
    }
}