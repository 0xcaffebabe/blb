package wang.ismy.blb.impl.seller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.api.seller.pojo.SellerInfoDTO;
import wang.ismy.blb.api.seller.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.seller.pojo.dto.SellerCreateDTO;
import wang.ismy.blb.api.seller.pojo.dto.SellerRegisterResultDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.JsonUtils;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.seller.service.SellerService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SellerApiImplTest {

    private MockMvc mockMvc;
    private SellerService sellerService;

    @BeforeEach
    public void setUp() throws Exception {
        sellerService = mock(SellerService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new SellerApiImpl(sellerService)).build();
    }

    @Test
    void getSellerInfo() throws Exception {
        Long sellerId = 1L;
        SellerInfoDTO sellerInfoDTO = MockUtils.create(SellerInfoDTO.class);
        when(sellerService.getSellerInfo(eq(sellerId))).thenReturn(sellerInfoDTO);

        mockMvc.perform(get("/v1/api/info/" + sellerId)

        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(sellerInfoDTO))));
    }

    @Test
    void register() throws Exception {
        SellerCreateDTO sellerCreateDTO = MockUtils.create(SellerCreateDTO.class);
        SellerRegisterResultDTO resultDTO = MockUtils.create(SellerRegisterResultDTO.class);
        when(sellerService.register(eq(sellerCreateDTO))).thenReturn(resultDTO);

        mockMvc.perform(post("/v1/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(sellerCreateDTO))

        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(resultDTO))));
    }

    @Test
    void login() throws Exception {
        String loginStr = "cxk";
        String password = "123";
        LoginResultDTO loginResultDTO  = MockUtils.create(LoginResultDTO.class);
        when(sellerService.login(eq(loginStr),eq(password))).thenReturn(loginResultDTO);

        mockMvc.perform(post("/v1/api/login")
                .param("loginStr",loginStr)
                .param("password",password)

        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(loginResultDTO))));
    }

    @Test
    void getSellerInfoByUsername () throws Exception {
        String username = "cxk";
        SellerInfoDTO dto = MockUtils.create(SellerInfoDTO.class);
        when(sellerService.getSellerInfo(eq(username))).thenReturn(dto);

        mockMvc.perform(get("/v1/api/info/username")
            .param("username",username)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(dto))));
    }
}