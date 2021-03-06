package wang.ismy.blb.aggregation.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.aggregation.client.consumer.ConsumerApiClient;
import wang.ismy.blb.aggregation.client.consumer.ConsumerDeliveryApiClient;
import wang.ismy.blb.aggregation.pojo.DeliveryShowDTO;
import wang.ismy.blb.api.consumer.pojo.dto.ConsumerDTO;
import wang.ismy.blb.api.consumer.pojo.dto.DeliveryDTO;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.JsonUtils;
import wang.ismy.blb.common.util.MockUtils;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeliveryAggApiTest {

    MockMvc mockMvc;
    ConsumerApiClient consumerApiClient;
    ConsumerDeliveryApiClient deliveryApiClient;
    @BeforeEach
    void setup(){
        consumerApiClient = mock(ConsumerApiClient.class);
        deliveryApiClient = mock(ConsumerDeliveryApiClient.class);
        mockMvc = MockMvcBuilders.standaloneSetup(
                new DeliveryAggApi(deliveryApiClient,consumerApiClient)
        ).build();
    }


    @Test
    void getDefaultDelivery() throws Exception {
        DeliveryDTO deliveryDTO = MockUtils.create(DeliveryDTO.class);
        ConsumerDTO consumerDTO = MockUtils.create(ConsumerDTO.class);
        when(deliveryApiClient.getDefaultDeliveryInfo()).thenReturn(Result.success(deliveryDTO));
        when(consumerApiClient.getInfo()).thenReturn(Result.success(consumerDTO));
        DeliveryShowDTO showDTO = new DeliveryShowDTO();
        BeanUtils.copyProperties(deliveryDTO,showDTO);
        BeanUtils.copyProperties(consumerDTO,showDTO);

        mockMvc.perform(get("/delivery/default")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(showDTO))));
    }
    
    @Test
    void getDeliveryList() throws Exception {
        var list =  MockUtils.create(DeliveryDTO.class,10);
        when(deliveryApiClient.getDeliveryInfoList()).thenReturn(Result.success(list));
        mockMvc.perform(get("/delivery"))
                .andExpect(content().json(JsonUtils.parse(Result.success(list))));
    }

    @Test
    void updateDelivery() throws Exception {
        DeliveryDTO deliveryDTO = MockUtils.create(DeliveryDTO.class);
        Long deliveryId = 1L;
        when(deliveryApiClient.updateDelivery(eq(deliveryId),eq(deliveryDTO))).thenReturn(Result.success());

        mockMvc.perform(put("/delivery/"+deliveryId)
            .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.parse(deliveryDTO))
        ).andExpect(content().json(JsonUtils.parse(Result.success())));
    }

    @Test
    void addDelivery() throws Exception {
        DeliveryDTO deliveryDTO = MockUtils.create(DeliveryDTO.class);
        when(deliveryApiClient.addDelivery(eq(deliveryDTO))).thenReturn(Result.success());

        mockMvc.perform(post("/delivery/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.parse(deliveryDTO))
        ).andExpect(content().json(JsonUtils.parse(Result.success())));
    }

    @Test
    void deleteDelivery () throws Exception {
        Long deliveryId = 1L;
        when(deliveryApiClient.deleteDelivery(eq(deliveryId))).thenReturn(Result.success());

        mockMvc.perform(delete("/delivery/"+deliveryId)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(content().json(JsonUtils.parse(Result.success())));
    }
}