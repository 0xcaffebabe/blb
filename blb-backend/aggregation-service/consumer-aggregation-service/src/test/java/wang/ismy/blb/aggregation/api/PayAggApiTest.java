package wang.ismy.blb.aggregation.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.aggregation.client.PayApiClient;
import wang.ismy.blb.api.pay.pojo.PayInfoDTO;
import wang.ismy.blb.api.pay.pojo.PayStatusDTO;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.JsonUtils;
import wang.ismy.blb.common.util.MockUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PayAggApiTest {

    PayApiClient payApiClient;
    MockMvc mockMvc;
    @BeforeEach
    void setup(){
        payApiClient = mock(PayApiClient.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new PayAggApi(payApiClient)).build();
    }

    @Test
    void generatePay() throws Exception {
        Long orderId = 1L;
        String payId = "1";
        when(payApiClient.generatePay(eq(orderId),eq(0))).thenReturn(Result.success(payId));
        mockMvc.perform(post("/pay/order/"+orderId)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(payId))));
    }

    @Test
    void pay() throws Exception {
        String payId = "1";
        PayInfoDTO payInfo = MockUtils.create(PayInfoDTO.class);
        when(payApiClient.pay(eq(Long.parseLong(payId)))).thenReturn(Result.success(payInfo));
        mockMvc.perform(get("/pay/"+payId)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(payInfo))));
    }

    @Test
    void getPayStatus() throws Exception {
        Long payId = 1L;
        PayStatusDTO dto = MockUtils.create(PayStatusDTO.class);
        when(payApiClient.getPayStatus(eq(payId))).thenReturn(Result.success(dto));
        mockMvc.perform(get("/pay/status/"+payId))
                .andExpect(content().json(JsonUtils.parse(Result.success(dto))));

    }
}