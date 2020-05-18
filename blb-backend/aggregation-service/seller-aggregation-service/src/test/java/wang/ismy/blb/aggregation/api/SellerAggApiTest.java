package wang.ismy.blb.aggregation.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.aggregation.client.*;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.auth.UserTypeEnum;
import wang.ismy.blb.api.seller.pojo.SellerInfoDTO;
import wang.ismy.blb.api.seller.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.seller.pojo.dto.SellerRegisterResultDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopCategoryDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopCreateDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.JsonUtils;
import wang.ismy.blb.common.util.MockUtils;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SellerAggApiTest {

    MockMvc mockMvc;
    SellerApiClient sellerApiClient;
    ShopApiClient shopApiClient;
    UploadApiClient uploadApiClient;
    ShopCategoryApiClient shopCategoryApiClient;
    AuthApiClient authApiClient;
    @BeforeEach
    void setup (){
        sellerApiClient = mock(SellerApiClient.class);
        shopApiClient = mock(ShopApiClient.class);
        shopCategoryApiClient = mock(ShopCategoryApiClient.class);
        authApiClient = mock(AuthApiClient.class);
        mockMvc = MockMvcBuilders.standaloneSetup(
                new SellerAggApi(sellerApiClient,
                        shopApiClient,
                        uploadApiClient,
                        shopCategoryApiClient,
                        authApiClient
                        )
        ).build();
    }

    @Test
    void loginOrRegisterWithNoRegister() throws Exception {
        String username = "cxk";
        String password = "123";
        when(sellerApiClient.getSellerInfo(eq(username))).thenReturn(Result.success(MockUtils.create(SellerInfoDTO.class)));
        LoginResultDTO loginResultDTO = MockUtils.create(LoginResultDTO.class);
        when(sellerApiClient.login(eq(username),eq(password)))
                .thenReturn(Result.success(loginResultDTO));
        mockMvc.perform(post("/login")
                .param("username",username)
                .param("password",password)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(loginResultDTO))));
    }

    @Test
    void loginOrRegisterWithRegister() throws Exception {
        String username = "cxk";
        String password = "123";
        when(sellerApiClient.getSellerInfo(eq(username))).thenReturn(Result.success(null));

        SellerRegisterResultDTO registerResultDTO = MockUtils.create(SellerRegisterResultDTO.class);
        when(sellerApiClient.register(argThat(dto->dto.getUsername().equals(username) && dto.getPassword().equals(password))))
                .thenReturn(Result.success(registerResultDTO));


        LoginResultDTO loginResultDTO = MockUtils.create(LoginResultDTO.class);
        when(sellerApiClient.login(eq(username),eq(password)))
                .thenReturn(Result.success(loginResultDTO));

        LoginResultDTO loginResultDTO1 = new LoginResultDTO();
        BeanUtils.copyProperties(loginResultDTO,loginResultDTO1);
        loginResultDTO1.setGreeting(registerResultDTO.getGreeting()+",您是本系统第"+registerResultDTO.getSellerNumber()+"位商家");
        mockMvc.perform(post("/login")
                .param("username",username)
                .param("password",password)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(loginResultDTO1))));
    }

    @Test
    void shopRegister () throws Exception {
        ShopCreateDTO shopCreateDTO = MockUtils.create(ShopCreateDTO.class);
        when(shopApiClient.addShop(eq(shopCreateDTO))).thenReturn(Result.success("1"));
        mockMvc.perform(post("/shop/register")
            .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.parse(shopCreateDTO))
        )
                .andExpect(content().json(JsonUtils.parse(Result.success("1"))));
    }

    @Test
    void getCategory() throws Exception {
        Integer level = 1;
        var list = MockUtils.create(ShopCategoryDTO.class,10);
        when(shopCategoryApiClient.getCategoryByLevel(eq(level)))
                .thenReturn(Result.success(list));
        mockMvc.perform(get("/shop/category")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(list))));
    }

    @Test
    void getShopInfo() throws Exception {
        String token = "token";
        User user = new User();
        user.setUserId(1L);
        user.setUserType(UserTypeEnum.SELLER.getType());
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));

        ShopInfoDTO shopInfoDTO = MockUtils.create(ShopInfoDTO.class);
        when(shopApiClient.getShopBySeller(eq(1L))).thenReturn(Result.success(shopInfoDTO));
        mockMvc.perform(get("/shop/info")
            .header(SystemConstant.TOKEN,token)
        )
                .andExpect(content().json(JsonUtils.parse(Result.success(shopInfoDTO))));
    }
}