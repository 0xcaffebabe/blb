package wang.ismy.blb.aggregation.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.aggregation.client.ProductCategoryApiClient;
import wang.ismy.blb.aggregation.client.ShopApiClient;
import wang.ismy.blb.aggregation.client.ShopCategoryApiClient;
import wang.ismy.blb.aggregation.pojo.CategoryShopDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ShopProductDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopItemDTO;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.JsonUtils;
import wang.ismy.blb.common.util.MockUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShopAggApiTest {

    MockMvc mockMvc;
    ShopApiClient shopApiClient;
    ProductCategoryApiClient productCategoryApiClient;
    @BeforeEach
    void setup(){
        shopApiClient = mock(ShopApiClient.class);
        productCategoryApiClient = mock(ProductCategoryApiClient.class);
        mockMvc = MockMvcBuilders.standaloneSetup(
                new ShopAggApi(shopApiClient,productCategoryApiClient)
        ).build();
    }

    @Test
    void getNearbyShop() throws Exception {
        String location = "117,29";

        ShopItemDTO shopItem1 = new ShopItemDTO();
        shopItem1.setShopId(1L);
        shopItem1.setShopName("1");
        ShopItemDTO shopItem2 = new ShopItemDTO();
        shopItem2.setShopId(2L);
        shopItem2.setShopName("2");
        when(shopApiClient.getNearbyShop(eq(location),argThat(pageable->
                pageable.getPage().equals(1L) && pageable.getSize().equals(10L)
        ))).thenReturn(Result.success(new Page<>(2L, List.of(shopItem1,shopItem2))));

        ShopInfoDTO shopInfo1 = new ShopInfoDTO();
        shopInfo1.setStartingPrice(new BigDecimal("10"));
        shopInfo1.setDeliveryFee(new BigDecimal("2"));
        ShopInfoDTO shopInfo2 = new ShopInfoDTO();
        shopInfo2.setStartingPrice(new BigDecimal("10"));
        shopInfo2.setDeliveryFee(new BigDecimal("2"));
        when(shopApiClient.getShopInfo(anyList())).thenReturn(Result.success(
                Map.of(1L,shopInfo1,2L,shopInfo2)
        ));

        CategoryShopDTO shop1 = new CategoryShopDTO();
        shop1.setShopId(1L);
        shop1.setShopName("1");
        shop1.setDeliveryTime("38分钟");
        shop1.setStartingPrice(new BigDecimal("10"));
        shop1.setDeliveryFee(new BigDecimal("2"));
        shop1.setSales(25);
        CategoryShopDTO shop2 = new CategoryShopDTO();
        shop2.setShopId(2L);
        shop2.setShopName("2");
        shop2.setDeliveryTime("38分钟");
        shop2.setStartingPrice(new BigDecimal("10"));
        shop2.setDeliveryFee(new BigDecimal("2"));
        shop2.setSales(25);
        mockMvc.perform(get("/shop/vicinity")
                .param("location",location)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(new Page(2L,List.of(shop1,shop2))))));

    }

    @Test
    void getShopInfo() throws Exception {
        Long shopId = 1L;
        ShopInfoDTO shopInfoDTO = MockUtils.create(ShopInfoDTO.class);
        when(shopApiClient.getShopInfo(eq(shopId))).thenReturn(Result.success(shopInfoDTO));

        mockMvc.perform(get("/shop/info/"+shopId))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(shopInfoDTO))));
    }

    @Test
    void getCategoryList() throws Exception {
        Long shopId = 1L;
        var list = MockUtils.create(ProductCategoryDTO.class,5);
        when(productCategoryApiClient.getCategoryList(eq(shopId))).thenReturn(Result.success(list));

        mockMvc.perform(get("/shop/"+shopId+"/category"))
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(list))));
    }

    @Test
    void getProductByCategory() throws Exception {
        Long shopId =1L;
        Long categoryId = 1L;
        var list = MockUtils.create(ShopProductDTO.class,5);
        when(productCategoryApiClient.getProductByCategory(eq(categoryId))).thenReturn(Result.success(list));
        mockMvc.perform(get("/shop/"+shopId+"/"+categoryId+"/product"))
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(list))));

    }
}