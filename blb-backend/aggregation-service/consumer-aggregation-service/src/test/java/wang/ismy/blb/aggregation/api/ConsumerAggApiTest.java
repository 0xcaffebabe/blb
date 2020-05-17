package wang.ismy.blb.aggregation.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.aggregation.client.AuthApiClient;
import wang.ismy.blb.aggregation.client.UploadApiClient;
import wang.ismy.blb.aggregation.client.consumer.ConsumerApiClient;
import wang.ismy.blb.api.consumer.pojo.dto.*;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.JsonUtils;
import wang.ismy.blb.common.util.MockUtils;

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
    UploadApiClient uploadApiClient;
    @BeforeEach
    void setup(){
        consumerApiClient = mock(ConsumerApiClient.class);
        uploadApiClient = mock(UploadApiClient.class);
        mockMvc = MockMvcBuilders.standaloneSetup(
                new ConsumerAggApi(consumerApiClient,uploadApiClient)
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

    @Test
    void updateInfo() throws Exception {
        ConsumerUpdateDTO consumerUpdateDTO = MockUtils.create(ConsumerUpdateDTO.class);
        when(consumerApiClient.updateInfo(eq(consumerUpdateDTO))).thenReturn(Result.success());

        mockMvc.perform(post("/info")
            .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.parse(consumerUpdateDTO))
        ).andExpect(content().json(JsonUtils.parse(Result.success())));

    }

    @Test
    void updatePassword() throws Exception {
        String oldPassword = "123";
        String newPassword = "456";
        when(consumerApiClient.updatePassword(eq(oldPassword),eq(newPassword))).thenReturn(Result.success());

        mockMvc.perform(post("/info/password")
                .param("oldPassword",oldPassword)
                .param("newPassword",newPassword)
        ).andExpect(content().json(JsonUtils.parse(Result.success())));
    }

    @Test
    void getInfo() throws Exception {
        ConsumerDTO dto = MockUtils.create(ConsumerDTO.class);
        when(consumerApiClient.getInfo()).thenReturn(Result.success(dto));

        mockMvc.perform(get("/info"))
                .andExpect(content().json(JsonUtils.parse(Result.success(dto))));
    }
}