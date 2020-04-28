package wang.ismy.blb.impl.rider.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.api.rider.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.rider.pojo.dto.RegisterDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.rider.service.RiderService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RiderApiImplTest {

    private MockMvc mockMvc;
    private RiderService riderService;

    @BeforeEach
    public void setUp() throws Exception {
        riderService = mock(RiderService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new RiderApiImpl(riderService)).build();
    }

    @Test
    void register() throws Exception{
        RegisterDTO registerDTO = MockUtils.create(RegisterDTO.class);
        when(riderService.register(eq(registerDTO))).thenReturn("注册成功");
        mockMvc.perform(post("/v1/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(registerDTO))
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success("注册成功"))));
    }

    @Test
    void login() throws Exception {
        String loginStr = "cxk";
        String password = "123";
        LoginResultDTO loginResultDTO = MockUtils.create(LoginResultDTO.class);
        when(riderService.login(eq(loginStr),eq(password))).thenReturn(loginResultDTO);
        mockMvc.perform(post("/v1/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .param("loginStr",loginStr)
                .param("password",password)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(loginResultDTO))));
    }
}