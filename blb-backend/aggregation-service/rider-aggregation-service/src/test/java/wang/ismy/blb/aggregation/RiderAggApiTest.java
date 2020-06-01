package wang.ismy.blb.aggregation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.aggregation.client.AuthApiClient;
import wang.ismy.blb.aggregation.client.RiderApiClient;
import wang.ismy.blb.api.rider.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.rider.pojo.dto.RegisterDTO;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.JsonUtils;
import wang.ismy.blb.common.util.MockUtils;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RiderAggApiTest {

    MockMvc mockMvc;
    RiderApiClient riderApiClient;

    @BeforeEach
    void setup(){
        riderApiClient = mock(RiderApiClient.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new RiderAggApi(riderApiClient)).build();
    }

    @Test
    void register() throws Exception {
        var dto = MockUtils.create(RegisterDTO.class);
        String result = "result";
        when(riderApiClient.register(eq(dto))).thenReturn(Result.success(result));
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.parse(dto))
        )
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(result))));
    }

    @Test
    void login() throws Exception {
        String username = "Cxk";
        String password = "123";
        var dto = MockUtils.create(LoginResultDTO.class);
        when(riderApiClient.login(eq(username),eq(password))).thenReturn(Result.success(dto));
        mockMvc.perform(post("/login")
                .param("username",username)
                .param("password",password)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(dto))));
    }
}