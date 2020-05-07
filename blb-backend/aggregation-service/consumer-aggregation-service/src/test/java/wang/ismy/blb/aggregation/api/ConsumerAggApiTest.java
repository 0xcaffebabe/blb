package wang.ismy.blb.aggregation.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.aggregation.client.ConsumerApiClient;
import wang.ismy.blb.aggregation.client.ShopApiClient;
import wang.ismy.blb.aggregation.client.ShopCategoryApiClient;
import wang.ismy.blb.api.consumer.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.consumer.pojo.dto.RegisterDTO;
import wang.ismy.blb.api.consumer.pojo.dto.RegisterResultDTO;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ConsumerAggApiTest {

    MockMvc mockMvc;
    ConsumerApiClient consumerApiClient;
    @BeforeEach
    void setup(){
        consumerApiClient = mock(ConsumerApiClient.class);
        mockMvc = MockMvcBuilders.standaloneSetup(
                new ConsumerAggApi(consumerApiClient)
        ).build();
    }

    @Test
    void login() throws Exception {
        String username = "cxk";
        String password = "123";
        LoginResultDTO loginResultDTO = MockUtils.create(LoginResultDTO.class);
        when(consumerApiClient.login(username,password)).thenReturn(Result.success(loginResultDTO));
        mockMvc.perform(post("/login")
                .param("username",username)
                .param("password",password)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(loginResultDTO))));
    }

    @Test
    void register() throws Exception {
        RegisterDTO registerDTO = MockUtils.create(RegisterDTO.class);
        RegisterResultDTO result = MockUtils.create(RegisterResultDTO.class);
        when(consumerApiClient.register(eq(registerDTO))).thenReturn(Result.success(result));

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(registerDTO))
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(result))));
    }
}