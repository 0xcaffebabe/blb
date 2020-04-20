package wang.ismy.blb.impl.product.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.api.product.pojo.ProductCategoryDO;
import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductCreateDTO;
import wang.ismy.blb.api.product.pojo.dto.ShopProductDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopCategoryDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.product.service.ProductSellerService;
import wang.ismy.blb.impl.product.service.ProductService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;
class ProductSellerApiImplTest {

    private MockMvc mockMvc;
    private ProductSellerService sellerService;


    @BeforeEach
    public void setUp() throws Exception {
        sellerService = mock(ProductSellerService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductSellerApiImpl(sellerService)).build();
    }

    @Test
    void addProduct() throws Exception {
        String token = "token";
        ProductCreateDTO dto = MockUtils.create(ProductCreateDTO.class);
        mockMvc.perform(post("/v1/api/seller")
                .header(SystemConstant.TOKEN,token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success())));
        verify(sellerService).addProduct(eq(token),eq(dto));
    }

    @Test
    void updateProduct() throws Exception {
        String token = "token";
        Long productId = 1L;
        ProductCreateDTO dto = MockUtils.create(ProductCreateDTO.class);
        mockMvc.perform(put("/v1/api/seller/"+productId)
                .header(SystemConstant.TOKEN,token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success())));
        verify(sellerService).updateProduct(eq(token),eq(productId),eq(dto));
    }

    @Test
    void deleteProduct() throws Exception {
        String token = "token";
        Long productId = 1L;
        mockMvc.perform(delete("/v1/api/seller/"+productId)
                .header(SystemConstant.TOKEN,token))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success())));
        verify(sellerService).deleteProduct(eq(token),eq(productId));
    }

    @Test
    void getProductList() throws Exception {
        String token = "token";
        var list = MockUtils.create(ShopProductDTO.class,5);
        when(sellerService.getProductList(eq(token),any())).thenReturn(new Page<>(5L,list));
        mockMvc.perform(get("/v1/api/seller/list")
                .header(SystemConstant.TOKEN,token)
        .param("page","1").param("size","5"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(new Page<>(5L,list)))));
    }

    @Test
    void getCategoryList() throws Exception {
        String token = "token";
        var list = MockUtils.create(ProductCategoryDTO.class,5);
        when(sellerService.getCategoryList(eq(token))).thenReturn(list);
        mockMvc.perform(get("/v1/api/seller/category/list")
                .header(SystemConstant.TOKEN,token))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(list))));
    }
}