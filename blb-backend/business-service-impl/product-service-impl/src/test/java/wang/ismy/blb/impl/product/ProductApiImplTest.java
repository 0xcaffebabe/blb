package wang.ismy.blb.impl.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.api.product.pojo.dto.CartProductGetDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductSpecDTO;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.product.api.ProductApiImpl;
import wang.ismy.blb.impl.product.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductApiImplTest {

    private MockMvc mockMvc;
    private ProductService productService;


    @BeforeEach
    public void setUp() throws Exception {
        productService = mock(ProductService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductApiImpl(productService)).build();
    }

    @Test
    void testGetProductById() throws Exception {
        Long id = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(1L);
        productDTO.setProductName("product1");
        when(productService.getProduct(eq(id))).thenReturn(productDTO);
        mockMvc.perform(get("/v1/api/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(productDTO))));
    }

    @Test
    void testGetProductByIdFailure() throws Exception {
        Long id = 1L;
        when(productService.getProduct(eq(id))).thenReturn(null);
        mockMvc.perform(get("/v1/api/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.failure(ResultCode.PRODUCT_NOT_EXIST))));
    }

    @Test
    void testGetProductList() throws Exception {
        List<Long> idList = List.of(1L, 2L, 3L);
        List<ProductDTO> resultList = MockUtils.create(ProductDTO.class, 3);
        when(productService.getProductList(argThat(list ->
                list.get(0).equals(1L) &&
                        list.get(1).equals(2L) &&
                        list.get(2).equals(3L)
        ))).thenReturn(resultList);

        mockMvc.perform(get("/v1/api/list").param("productIdList",idList.get(0).toString(),idList.get(1).toString(),idList.get(2).toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(resultList))));
    }

    @Test
    void testGetListByProductAndSpecList() throws Exception {
        List<CartProductGetDTO> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            CartProductGetDTO cartProductGetDTO = new CartProductGetDTO();
            cartProductGetDTO.setProductId((long) i);
            cartProductGetDTO.setSpecId((long) i);
            list.add(cartProductGetDTO);
        }
        List<ProductDTO> resultList = MockUtils.create(ProductDTO.class, 3);
        when(productService.getProductAndSpecList(argThat(a ->
                a.size() == 3
        ))).thenReturn(resultList);
        mockMvc.perform(get("/v1/api/spec/list").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsBytes(list)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(resultList))));
    }

    @Test void testGetSpec() throws Exception {
        Long specId = 1L;
        ProductSpecDTO spec = MockUtils.create(ProductSpecDTO.class);

        when(productService.getProductSpec(eq(specId))).thenReturn(spec);

        mockMvc.perform(get("/v1/api/spec/"+specId))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(spec))));
    }

    @Test void testGetSpecFailure() throws Exception {
        Long specId = 1L;
        ProductSpecDTO spec = MockUtils.create(ProductSpecDTO.class);

        when(productService.getProductSpec(eq(specId))).thenReturn(null);

        mockMvc.perform(get("/v1/api/spec/"+specId))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.failure(ResultCode.PRODUCT_SPEC_NOT_EXIST))));
    }
}