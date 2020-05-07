package wang.ismy.blb.aggregation.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.aggregation.client.ShopApiClient;
import wang.ismy.blb.aggregation.client.ShopCategoryApiClient;
import wang.ismy.blb.aggregation.pojo.CategoryShopDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopCategoryDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopItemDTO;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryAggApiTest {

    MockMvc mockMvc;
    ShopCategoryApiClient shopCategoryApiClient;
    ShopApiClient shopApiClient;
    @BeforeEach
    void setup(){
        shopCategoryApiClient = mock(ShopCategoryApiClient.class);
        shopApiClient = mock(ShopApiClient.class);
        mockMvc = MockMvcBuilders.standaloneSetup(
                new CategoryAggApi(shopCategoryApiClient,shopApiClient)
        ).build();
    }

    @Test
    void getCategory() throws Exception {
        Integer level = 1;
        var list = MockUtils.create(ShopCategoryDTO.class,10);
        when(shopCategoryApiClient.getCategoryByLevel(eq(level)))
                .thenReturn(Result.success(list));
        mockMvc.perform(get("/category/"+level)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(list))));
    }

    @Test
    void getShop() throws Exception {
        String location = "117,29";
        Long categoryId = 1L;

        ShopItemDTO shopItem1 = new ShopItemDTO();
        shopItem1.setShopId(1L);
        shopItem1.setShopName("1");
        ShopItemDTO shopItem2 = new ShopItemDTO();
        shopItem2.setShopId(2L);
        shopItem2.setShopName("2");
        when(shopCategoryApiClient.getShopByCategory(eq(categoryId),eq(location),argThat(pageable->
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
        mockMvc.perform(get("/category/"+categoryId+"/shop")
            .param("location",location)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(new Page(2L,List.of(shop1,shop2))))));

    }
}