package wang.ismy.blb.aggregation.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jdi.event.ExceptionEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.aggregation.client.*;
import wang.ismy.blb.aggregation.client.order.OrderApiClient;
import wang.ismy.blb.aggregation.client.order.OrderSellerApiClient;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.auth.UserTypeEnum;
import wang.ismy.blb.api.order.enums.OrderStatusEnum;
import wang.ismy.blb.api.order.pojo.dto.NewOrderItemDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderResultDTO;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderDetailDTO;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderItemDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductCreateDTO;
import wang.ismy.blb.api.product.pojo.dto.ShopProductDTO;
import wang.ismy.blb.api.seller.pojo.SellerInfoDTO;
import wang.ismy.blb.api.seller.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.seller.pojo.dto.SellerRegisterResultDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopCategoryDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopCreateDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoUpdateDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.JsonUtils;
import wang.ismy.blb.common.util.MockUtils;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SellerAggApiTest {

    MockMvc mockMvc;
    SellerApiClient sellerApiClient;
    ShopApiClient shopApiClient;
    UploadApiClient uploadApiClient;
    ShopCategoryApiClient shopCategoryApiClient;
    AuthApiClient authApiClient;
    OrderSellerApiClient orderSellerApiClient;
    ProductCategoryApiClient productCategoryApiClient;
    ProductSellerApiClient productSellerApiClient;
    ProductApiClient productApiClient;
    OrderApiClient orderApiClient;
    @BeforeEach
    void setup (){
        sellerApiClient = mock(SellerApiClient.class);
        shopApiClient = mock(ShopApiClient.class);
        shopCategoryApiClient = mock(ShopCategoryApiClient.class);
        authApiClient = mock(AuthApiClient.class);
        orderSellerApiClient = mock(OrderSellerApiClient.class);
        productCategoryApiClient = mock(ProductCategoryApiClient.class);
        productSellerApiClient = mock(ProductSellerApiClient.class);
        productApiClient = mock(ProductApiClient.class);
        orderApiClient = mock(OrderApiClient.class);
        mockMvc = MockMvcBuilders.standaloneSetup(
                new SellerAggApi(sellerApiClient,
                        shopApiClient,
                        uploadApiClient,
                        shopCategoryApiClient,
                        authApiClient,
                        orderSellerApiClient,
                        orderApiClient,
                        productCategoryApiClient,
                        productSellerApiClient,
                        productApiClient
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

    @Test
    void getNewOrderList () throws Exception {
        var list = MockUtils.create(NewOrderItemDTO.class,10);
        when(orderSellerApiClient.getNewOrderList()).thenReturn(Result.success(list));
        mockMvc.perform(get("/shop/order/new")
        )
                .andExpect(content().json(JsonUtils.parse(Result.success(list))));
    }

    @Test
    void addCategory () throws Exception {
        String token = "token";
        var dto = MockUtils.create(ProductCategoryDTO.class);
        when(productCategoryApiClient.addCategory(eq(token),eq(dto))).thenReturn(Result.success());

        mockMvc.perform(post("/shop/category")
                .header(SystemConstant.TOKEN,token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.parse(dto))
        )
                .andExpect(content().json(JsonUtils.parse(Result.success())));
    }

    @Test
    void updateCategory() throws Exception {
        String token = "token";
        Long categoryId =1L;
        var dto = MockUtils.create(ProductCategoryDTO.class);
        when(productCategoryApiClient.updateCategory(eq(token),argThat(d->d.getCategoryId().equals(categoryId)))).thenReturn(Result.success());
        mockMvc.perform(put("/shop/category/"+categoryId)
                .header(SystemConstant.TOKEN,token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.parse(dto))
        )
                .andExpect(content().json(JsonUtils.parse(Result.success())));
    }

    @Test
    void deleteCategory() throws Exception {
        String token = "token";
        Long categoryId =1L;
        when(productCategoryApiClient.deleteCategory(eq(token),eq(categoryId))).thenReturn(Result.success());

        mockMvc.perform(delete("/shop/category/"+categoryId)
                .header(SystemConstant.TOKEN,token)
        )
                .andExpect(content().json(JsonUtils.parse(Result.success())));
    }

    @Test
    void getProduct() throws Exception {
        Long categoryId = 1L;
        var list = MockUtils.create(ShopProductDTO.class,10);
        when(productCategoryApiClient.getProductByCategory(eq(categoryId))).thenReturn(Result.success(list));

        mockMvc.perform(get("/shop/category/"+categoryId+"/product")
        )
                .andExpect(content().json(JsonUtils.parse(Result.success(list))));
    }

    @Test
    void addProduct() throws Exception {
        String token ="token";
        Long categoryId = 1L;
        var dto = MockUtils.create(ProductCreateDTO.class);
        when(productSellerApiClient.addProduct(eq(token),eq(dto))).thenReturn(Result.success());

        mockMvc.perform(post("/shop/category/"+categoryId+"/product")
                .header(SystemConstant.TOKEN,token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.parse(dto))
        )
                .andExpect(content().json(JsonUtils.parse(Result.success())));
    }

    @Test
    void updateProduct() throws Exception {
        String token ="token";
        Long categoryId = 1L;
        var dto = MockUtils.create(ProductCreateDTO.class);
        when(productSellerApiClient.updateProduct(eq(token),eq(dto.getProductId()),eq(dto))).thenReturn(Result.success());

        mockMvc.perform(put("/shop/category/"+categoryId+"/product")
                .header(SystemConstant.TOKEN,token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.parse(dto))
        )
                .andExpect(content().json(JsonUtils.parse(Result.success())));
    }

    @Test void updateStockFull() throws Exception {
        Long categoryId = 1L;
        Long productId = 1L;
        Long specId = 1L;
        when(productApiClient.updateStock(eq(productId),eq(specId),eq(9999L))).thenReturn(Result.success());

        mockMvc.perform(put("/shop/category/"+categoryId+"/product/"+productId+"/"+specId+"/stock")
                .param("type","full")
        )
                .andExpect(content().json(JsonUtils.parse(Result.success())));
    }

    @Test void updateStockEmpty() throws Exception {
        Long categoryId = 1L;
        Long productId = 1L;
        Long specId = 1L;
        when(productApiClient.updateStock(eq(productId),eq(specId),eq(0L))).thenReturn(Result.success());

        mockMvc.perform(put("/shop/category/"+categoryId+"/product/"+productId+"/"+specId+"/stock")
                .param("type","empty")
        )
                .andExpect(content().json(JsonUtils.parse(Result.success())));
    }

    @Test void updateShopInfo() throws Exception {
        String token = "token";
        User user = new User();
        user.setUserId(1L);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));

        ShopInfoDTO shopInfoDTO = new ShopInfoDTO();
        shopInfoDTO.setShopId(1L);
        when(shopApiClient.getShopBySeller(eq(1L))).thenReturn(Result.success(shopInfoDTO));

        var dto = MockUtils.create(ShopInfoUpdateDTO.class);

        when(shopApiClient.updateShopInfo(eq(1L),eq(dto))).thenReturn(Result.success());

        mockMvc.perform(put("/shop/info")
            .header(SystemConstant.TOKEN,token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.parse(dto))
        ).andExpect(content().json(JsonUtils.parse(Result.success())));
    }

    @Test void getSellerInfo() throws Exception {
        String token = "toiken";
        User user = new User();
        user.setUserId(1L);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        var dto = MockUtils.create(SellerInfoDTO.class);
        when(sellerApiClient.getSellerInfo(eq(1L))).thenReturn(Result.success(dto));
        mockMvc.perform(get("/seller/info")
                .header(SystemConstant.TOKEN,token)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(content().json(JsonUtils.parse(Result.success(dto))));
    }

    @Test void dinnerOut () throws Exception {
        String token = "token";
        Long orderId = 1L;
        User user = new User();
        user.setUserId(1L);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));

        ShopInfoDTO shopInfoDTO = new ShopInfoDTO();
        shopInfoDTO.setShopId(1L);
        when(shopApiClient.getShopBySeller(eq(1L))).thenReturn(Result.success(shopInfoDTO));

        OrderResultDTO orderResultDTO = new OrderResultDTO();
        orderResultDTO.setShopId(1L);
        when(orderApiClient.getOrder(eq(1L))).thenReturn(Result.success(orderResultDTO));

        mockMvc.perform(put("/shop/order/"+orderId+"/out")
                .header(SystemConstant.TOKEN,token)
        ).andExpect(content().json(JsonUtils.parse(Result.success())));

        verify(orderApiClient).updateOrderStatus(eq(orderId),eq(OrderStatusEnum.PROCESSED.getCode()));
    }

    @Test void getOrderList() throws Exception {
        var list = MockUtils.create(ConsumerOrderItemDTO.class,10);
        var page = new Page<>(10L, list);
        when(orderSellerApiClient.getSellerOrderList(anyString(),anyString(),eq(1L),eq(10L))).thenReturn(Result.success(page));
        mockMvc.perform(get("/shop/order/list")
                .param("page","1")
                .param("size","10")
        ).andExpect(content().json(JsonUtils.parse(Result.success(page))));
    }

    @Test void getOrderDetail () throws Exception {
        Long orderId = 1L;
        var dto = MockUtils.create(ConsumerOrderDetailDTO.class);
        when(orderSellerApiClient.getSellerOrderDetail(eq(orderId))).thenReturn(Result.success(dto));

        mockMvc.perform(get("/shop/order/"+orderId)
        ).andExpect(content().json(JsonUtils.parse(Result.success(dto))));
    }
}