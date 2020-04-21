package wang.ismy.blb.impl.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.api.consumer.pojo.dto.*;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.consumer.service.ConsumerService;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ConsumerApiImplTest {

    private MockMvc mockMvc;
    private ConsumerService consumerService;


    @BeforeEach
    public void setUp() throws Exception {
        consumerService = mock(ConsumerService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ConsumerApiImpl(consumerService)).build();
    }

    @Test
    void register() throws Exception {
        RegisterDTO registerDTO = MockUtils.create(RegisterDTO.class);
        RegisterResultDTO registerResultDTO = MockUtils.create(RegisterResultDTO.class);
        when(consumerService.register(eq(registerDTO))).thenReturn(registerResultDTO);

        mockMvc.perform(post("/v1/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(registerDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(registerResultDTO))));
    }

    @Test
    void login() throws Exception {
        String loginStr = "usernamex";
        String password = "!23";

        LoginResultDTO loginResultDTO = MockUtils.create(LoginResultDTO.class);
        when(consumerService.login(eq(loginStr), eq(password))).thenReturn(loginResultDTO);

        mockMvc.perform(post("/v1/api/login").param("loginStr", loginStr).param("password", password))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(loginResultDTO))));
    }

    @Test
    void getInfo() throws Exception {
        String token = "token";
        ConsumerDTO consumerDTO = MockUtils.create(ConsumerDTO.class);

        when(consumerService.getInfo(eq(token))).thenReturn(consumerDTO);
        mockMvc.perform(get("/v1/api/").header("token", token))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(consumerDTO))));
    }

    @Test
    void testGetInfo() throws Exception {
        Long consumerId = 1L;
        ConsumerDTO consumerDTO = MockUtils.create(ConsumerDTO.class);

        when(consumerService.getInfo(eq(consumerId))).thenReturn(consumerDTO);
        mockMvc.perform(get("/v1/api/" + consumerId))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(consumerDTO))));
    }

    @Test
    void testGetInfo1() throws Exception {
        var consumerIdList = List.of(1L, 2L, 3L);
        var consumerMap = Map.of(
                1L, MockUtils.create(ConsumerDTO.class),
                2L, MockUtils.create(ConsumerDTO.class),
                3L, MockUtils.create(ConsumerDTO.class)
        );

        when(consumerService.getInfo(eq(consumerIdList))).thenReturn(consumerMap);
        mockMvc.perform(get("/v1/api/list")
                .param("consumerIdList", consumerIdList.get(0).toString())
                .param("consumerIdList", consumerIdList.get(1).toString())
                .param("consumerIdList", consumerIdList.get(2).toString())
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(consumerMap))));
    }

    @Test
    void updateInfo() throws Exception {
        String token = "token";
        ConsumerUpdateDTO consumerDTO = MockUtils.create(ConsumerUpdateDTO.class);
        mockMvc.perform(put("/v1/api/")
                .header(SystemConstant.TOKEN,token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(consumerDTO))
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success())));

        verify(consumerService).updateInfo(eq(token),eq(consumerDTO));
    }

    @Test
    void updatePassword() throws Exception {
        String oldPas = "123";
        String newPas = "456";
        String token = "token";
        mockMvc.perform(put("/v1/api/password")
                .header(SystemConstant.TOKEN,token)
                .param("oldPassword",oldPas)
                .param("newPassword",newPas)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success())));
        verify(consumerService).updatePassword(eq(token),eq(oldPas),eq(newPas));
    }
}