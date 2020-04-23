package wang.ismy.blb.impl.shop.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.api.shop.pojo.dto.ShopCategoryDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopItemDTO;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.shop.service.ShopCategoryService;
import wang.ismy.blb.impl.shop.service.ShopService;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShopCategoryApiImplTest {

    private MockMvc mockMvc;
    private ShopCategoryService categoryService;

    @BeforeEach
    public void setup() {
        categoryService = mock(ShopCategoryService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ShopCategoryApiImpl(categoryService)).build();
    }

    @Test
    void getCategoryByLevel() throws Exception {
        Integer level = 1;
        var list = MockUtils.create(ShopCategoryDTO.class, 5);
        when(categoryService.getCategoryByLevel(eq(level))).thenReturn(list);

        mockMvc.perform(get("/v1/api/category/" + level))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(list))));
    }

    @Test
    void getShopByCategory() throws Exception {
        Long categoryId = 1L;
        String location = "117,29";
        Pageable pageable = Pageable.of(1L, 5L);
        var list = MockUtils.create(ShopItemDTO.class, 5);
        var page = new Page<>(5L, list);
        when(categoryService.getShopByCategory(eq(categoryId), eq(location), eq(pageable))).thenReturn(page);

        mockMvc.perform(get("/v1/api/category/" + categoryId + "/shop")
                .param("location", location)
                .param("page", "1")
                .param("size", "5")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(page))));
    }
}