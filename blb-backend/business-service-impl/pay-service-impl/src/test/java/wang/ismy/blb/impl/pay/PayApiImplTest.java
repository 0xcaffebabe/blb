package wang.ismy.blb.impl.pay;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.api.pay.enums.PayTypeEnum;
import wang.ismy.blb.api.pay.pojo.PayStatusDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.pay.service.PayService;

import javax.ws.rs.PUT;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PayApiImplTest {

    private MockMvc mockMvc;
    private PayService payService;

    @BeforeEach
    public void setUp() throws Exception {
        payService = mock(PayService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new PayApiImpl(payService)).build();
    }

    @Test
    void generatePay() throws Exception {
        String token = "token";
        Long orderId = 1L;
        Integer type = PayTypeEnum.ALI_PAY.getCode();

        when(payService.generatePay(eq(token),eq(orderId),eq(type))).thenReturn(1L);

        mockMvc.perform(post("/v1/api/order/" + orderId).header(SystemConstant.TOKEN,token)
                .param("type",type.toString())
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(orderId.toString()))));
    }

    @Test
    void pay() throws Exception {
        Long payId = 1L;
        String ret = "pay string";
        when(payService.pay(eq(payId))).thenReturn(ret);

        mockMvc.perform(get("/v1/api/" + payId)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(ret))));
    }

    @Test
    void callback() throws Exception {
        mockMvc.perform(get("/v1/api/callback").header(SystemConstant.TOKEN,"1")
        )
                .andExpect(status().isOk());
        verify(payService).callback(argThat(req->req.getHeader(SystemConstant.TOKEN).equals("1")),any());
    }

    @Test
    void refund() throws Exception {
        String token = "token";
        Long orderId = 1L;
        mockMvc.perform(put("/v1/api/refund/" + orderId)
                .header(SystemConstant.TOKEN,token)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success())));
        verify(payService).refund(eq(token),eq(orderId));
    }

    @Test
    void getPayStatus() throws Exception {
        Long payId = 1L;
        PayStatusDTO payStatusDTO = MockUtils.create(PayStatusDTO.class);
        when(payService.getPayStatus(eq(payId))).thenReturn(payStatusDTO);
        mockMvc.perform(get("/v1/api/status/" + payId)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(payStatusDTO))));
    }
}