package wang.ismy.blb.impl.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ShopProductDTO;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.product.service.ProductCategoryService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductCategoryApiImplTest {

    private MockMvc mockMvc;
    private ProductCategoryService productCategoryService;

    @BeforeEach
    public void setup() {
        productCategoryService = mock(ProductCategoryService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductCategoryApiImpl(productCategoryService)).build();
    }

    @Test
    void testGetProductByCategory() throws Exception {
        List<ShopProductDTO> productList = MockUtils.create(ShopProductDTO.class, 5);
        Long categoryId = 1L;
        when(productCategoryService.getProductByCategory(eq(categoryId))).thenReturn(productList);
        mockMvc.perform(get("/v1/api/category/product/" + categoryId))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(productList))));
    }

    @Test
    void testGetCategoryList() throws Exception {
        List<ProductCategoryDTO> categoryList = MockUtils.create(ProductCategoryDTO.class, 5);
        Long shopId = 1L;
        when(productCategoryService.getCategoryList(eq(shopId))).thenReturn(categoryList);

        mockMvc.perform(get("/v1/api/category/shop/" + shopId))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(categoryList))));
    }

    @Test
    void testAddCategory() throws Exception {
        String token = "token";
        var productCategoryDTO = MockUtils.create(ProductCategoryDTO.class);

        mockMvc.perform(put("/v1/api/category/").
                contentType(MediaType.APPLICATION_JSON)
                .header("TOKEN", token).content(new ObjectMapper().writeValueAsBytes(productCategoryDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success())));
        verify(productCategoryService).addCategory(eq(token),eq(productCategoryDTO));
    }

    @Test
    void testUpdateCategory() throws Exception {
        String token = "token";
        var productCategoryDTO = MockUtils.create(ProductCategoryDTO.class);

        mockMvc.perform(post("/v1/api/category/").
                contentType(MediaType.APPLICATION_JSON)
                .header("TOKEN", token).content(new ObjectMapper().writeValueAsBytes(productCategoryDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success())));
        verify(productCategoryService).update(eq(token),eq(productCategoryDTO));
    }

    @Test
    void testDeleteCategory() throws Exception {
        String token = "token";
        Long cateId = 1L;

        mockMvc.perform(delete("/v1/api/category/"+cateId).
                contentType(MediaType.APPLICATION_JSON)
                .header("TOKEN", token))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success())));
        verify(productCategoryService).delete(eq(token),eq(cateId));
    }
}