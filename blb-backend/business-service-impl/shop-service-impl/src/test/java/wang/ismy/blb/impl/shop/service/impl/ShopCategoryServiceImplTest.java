package wang.ismy.blb.impl.shop.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.blb.api.shop.pojo.dto.ShopCategoryDTO;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.impl.shop.client.ProductEvalApiClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class ShopCategoryServiceImplTest {

    @Autowired
    ShopCategoryServiceImpl categoryService;

    @Test
    void getCategoryByLevel() {
        List<ShopCategoryDTO> list = categoryService.getCategoryByLevel(1);
        assertEquals(2, list.size());
        assertEquals("快餐", list.get(0).getCategoryName());
        assertEquals("简餐", list.get(0).getSubCategoryList().get(0).getCategoryName());
        assertEquals("汉堡可乐", list.get(0).getSubCategoryList()
                .get(0).getSubCategoryList()
                .get(0).getCategoryName());
        assertEquals("甜品", list.get(1).getCategoryName());
    }

    @Test
    void getShopByCategory() {
        Long categoryId = 2L;
        ProductEvalApiClient evalApiClient = mock(ProductEvalApiClient.class);
        var map = Map.of(1L,new BigDecimal("3.5"),2L,new BigDecimal("3.5"));
        when(evalApiClient.getShopEval(anyList())).thenReturn(Result.success(map));
        categoryService.setEvalApiClient(evalApiClient);

        var page = categoryService.getShopByCategory(categoryId,"117,29", Pageable.of(1L,5L));
        assertEquals(2,page.getTotal());
        assertEquals("黄焖鸡米饭",page.getData().get(0).getShopName());
        assertEquals(new BigDecimal("3.5"),page.getData().get(0).getRanking());
        assertEquals("阿牛很芒",page.getData().get(1).getShopName());
        assertEquals(new BigDecimal("3.5"),page.getData().get(1).getRanking());
    }
}