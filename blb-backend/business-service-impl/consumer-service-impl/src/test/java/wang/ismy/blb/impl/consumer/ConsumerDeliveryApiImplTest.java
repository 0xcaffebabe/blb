package wang.ismy.blb.impl.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.api.consumer.pojo.dto.DeliveryDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.consumer.service.ConsumerDeliveryService;
import wang.ismy.blb.impl.consumer.service.ConsumerService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;
class ConsumerDeliveryApiImplTest {
    private MockMvc mockMvc;
    private ConsumerDeliveryService deliveryService;


    @BeforeEach
    public void setUp() throws Exception {
        deliveryService = mock(ConsumerDeliveryService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ConsumerDeliveryApiImpl(deliveryService)).build();
    }
    @Test
    void addDelivery() throws Exception {
        DeliveryDTO dto = MockUtils.create(DeliveryDTO.class);
        String token = "token";
        mockMvc.perform(post("/v1/api/delivery")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(dto)).header(SystemConstant.TOKEN,token))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success())));
        verify(deliveryService).addDelivery(eq(token),eq(dto));
    }

    @Test
    void updateDelivery() throws Exception {
        DeliveryDTO dto = MockUtils.create(DeliveryDTO.class);
        Long deliveryId = 1L;
        String token = "token";
        mockMvc.perform(put("/v1/api/delivery/"+deliveryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(dto)).header(SystemConstant.TOKEN,token))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success())));
        verify(deliveryService).updateDelivery(eq(token),eq(deliveryId),eq(dto));
    }

    @Test
    void getDeliveryInfoList() throws Exception{
        var list = MockUtils.create(DeliveryDTO.class,5);
        String token = "token";
        when(deliveryService.getDeliveryInfoList(eq(token))).thenReturn(list);
        mockMvc.perform(get("/v1/api/delivery/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header(SystemConstant.TOKEN,token))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(list))));
    }

    @Test
    void getDefaultDeliveryInfo() throws Exception {
        DeliveryDTO dto = MockUtils.create(DeliveryDTO.class);
        String token = "token";
        when(deliveryService.getDefaultDeliveryInfo(eq(token))).thenReturn(dto);
        mockMvc.perform(get("/v1/api/delivery/default")
                .contentType(MediaType.APPLICATION_JSON)
                .header(SystemConstant.TOKEN,token))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(dto))));
    }

    @Test
    void testGetDefaultDeliveryInfo() throws Exception{
        DeliveryDTO dto = MockUtils.create(DeliveryDTO.class);
        Long consumerId = 1L;
        when(deliveryService.getDefaultDeliveryInfo(eq(consumerId))).thenReturn(dto);
        mockMvc.perform(get("/v1/api/delivery/default/"+consumerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(dto))));
    }

    @Test
    void getDeliveryInfo() throws Exception {
        DeliveryDTO dto = MockUtils.create(DeliveryDTO.class);
        Long deliveryId = 1L;
        when(deliveryService.getDeliveryInfo(eq(deliveryId))).thenReturn(dto);
        mockMvc.perform(get("/v1/api/delivery/"+deliveryId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(dto))));
    }
}