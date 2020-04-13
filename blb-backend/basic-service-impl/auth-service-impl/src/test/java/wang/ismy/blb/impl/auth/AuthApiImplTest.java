package wang.ismy.blb.impl.auth;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.auth.UserTypeEnum;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.impl.auth.service.AuthService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthApiImplTest {

    private MockMvc mockMvc;
    private AuthService authService;


    @BeforeEach
    public void setUp() throws Exception {
        authService = mock(AuthService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthApiImpl(authService)).build();
    }

    @Test
    void testAuth() throws Exception {
        User user = new User();
        user.setUsername("cxk");
        user.setUserId(1L);
        user.setUserType(UserTypeEnum.CONSUMER.getType());
        String content = new Gson().toJson(user);
        String token = "this is a token";

        when(authService.auth(argThat(u->
            u.getUsername().equals("cxk") && u.getUserId().equals(1L) && u.getUserType().equals(UserTypeEnum.CONSUMER.getType())
        ))).thenReturn(token);

        mockMvc.perform(put("/v1/api/").contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(Result.success(token))));

    }

    @Test
    void testAuthFailure() throws Exception {
        User user = new User();
        user.setUsername("cxk");
        user.setUserId(1L);
        user.setUserType(UserTypeEnum.CONSUMER.getType());

        when(authService.auth(argThat(u->u.getUsername().equals("cxk")))).thenReturn("");
        mockMvc.perform(put("/v1/api/").contentType(MediaType.APPLICATION_JSON_VALUE).content(new Gson().toJson(user)))
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(Result.failure(ResultCode.AUTH_ERROR))));
    }

    @Test
    void testValid() throws Exception {
        User user = new User();
        user.setUsername("cxk");
        String token = "toekn-token";
        when(authService.valid(eq(token))).thenReturn(user);

        mockMvc.perform(get("/v1/api/"+token))
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(Result.success(user))));
    }

    @Test
    void testValidFailure() throws Exception {
        User user = new User();
        user.setUsername("cxk");
        String token = "toekn-token";
        when(authService.valid(eq(token))).thenReturn(null);

        mockMvc.perform(get("/v1/api/"+token))
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(Result.failure(ResultCode.USER_NOT_EXIST))));
    }
}