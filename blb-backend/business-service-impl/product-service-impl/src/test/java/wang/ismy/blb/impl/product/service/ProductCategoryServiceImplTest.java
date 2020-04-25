package wang.ismy.blb.impl.product.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.order.OrderApi;
import wang.ismy.blb.api.product.pojo.ProductCategoryDO;
import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ShopProductDTO;
import wang.ismy.blb.common.SnowFlake;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.product.client.AuthApiClient;
import wang.ismy.blb.impl.product.client.OrderApiClient;
import wang.ismy.blb.impl.product.client.ShopApiClient;
import wang.ismy.blb.impl.product.repository.ProductCategoryRepository;
import wang.ismy.blb.impl.product.service.impl.ProductCategoryServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class ProductCategoryServiceImplTest {

    @Autowired
    ProductCategoryServiceImpl productCategoryService;

    @Autowired
    AuthApiClient authApiClient;


    @Autowired
    ShopApiClient shopApiClient;

    @Autowired
    SnowFlake snowFlake;

    @Test
    void testGetProductByCategory() {
        Long categoryId = 1L;
        OrderApiClient orderApiClient = mock(OrderApiClient.class);
        when(orderApiClient.getProductSales(anyList()))
                .thenReturn(Result.success(Map.of(1L,100L,2L,100L,3L,100L)));
        productCategoryService.setOrderApiClient(orderApiClient);
        List<ShopProductDTO> list = productCategoryService.getProductByCategory(categoryId);
        assertEquals(3, list.size());
        for (ShopProductDTO dto : list) {
            assertEquals(100L, dto.getSales());
            assertEquals(new BigDecimal(10L), dto.getPositiveRate());
        }
    }

    @Test
    void testGetCategoryList() {
        Long shopId = 1L;
        var list = productCategoryService.getCategoryList(shopId);
        assertEquals(2, list.size());
        assertEquals("1号店铺招牌菜", list.get(0).getCategoryName());
        assertEquals("1号店铺兼职", list.get(1).getCategoryName());
    }

    @Test
    void testAddCategory() {
        String token = "token";

        AuthApiClient authApiClient = mock(AuthApiClient.class);
        User user = new User();
        user.setUserId(1L);
        user.setUserType("商家");
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));

        Long nextId = 11L;
        var categoryDTO = MockUtils.create(ProductCategoryDTO.class);
        SnowFlake mockSnowFlake = mock(SnowFlake.class);
        when(mockSnowFlake.nextId()).thenReturn(nextId);
        ProductCategoryRepository mockRepository = mock(ProductCategoryRepository.class);
        ProductCategoryServiceImpl categoryService =
                new ProductCategoryServiceImpl(mockRepository, null, null, null, null, authApiClient, shopApiClient, mockSnowFlake);
        categoryService.addCategory(token, categoryDTO);
        verify(mockRepository).save(argThat(categoryDO ->
                categoryDO.getCategoryId().equals(nextId)
                        && categoryDO.getCategoryDesc().equals(categoryDTO.getCategoryDesc())
                        && categoryDO.getCategoryName().equals(categoryDTO.getCategoryName())
                        && categoryDO.getShopId().equals(1L)
        ));
    }

    @Test
    void testUpdate() {
        String token = "token";

        AuthApiClient authApiClient = mock(AuthApiClient.class);
        User user = new User();
        user.setUserId(1L);
        user.setUserType("商家");
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));

        Long cateId = 11L;
        var categoryDTO = MockUtils.create(ProductCategoryDTO.class);
        categoryDTO.setCategoryId(cateId);
        ProductCategoryRepository mockRepository = mock(ProductCategoryRepository.class);
        ProductCategoryDO dbCate = MockUtils.create(ProductCategoryDO.class);
        dbCate.setCategoryId(cateId);
        dbCate.setShopId(1L);
        when(mockRepository.findById(eq(cateId))).thenReturn(Optional.of(dbCate));

        ProductCategoryServiceImpl categoryService =
                new ProductCategoryServiceImpl(mockRepository, null, null, null, null, authApiClient, shopApiClient, null);
        categoryService.update(token, categoryDTO);
        verify(mockRepository).save(argThat(categoryDO ->
                categoryDO.getCategoryId().equals(cateId)
                        && categoryDO.getCategoryDesc().equals(categoryDTO.getCategoryDesc())
                        && categoryDO.getCategoryName().equals(categoryDTO.getCategoryName())
        ));
    }

    @Test
    void testDelete() {
        String token = "token";

        AuthApiClient authApiClient = mock(AuthApiClient.class);
        User user = new User();
        user.setUserId(1L);
        user.setUserType("商家");
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));

        Long cateId = 11L;
        var categoryDTO = MockUtils.create(ProductCategoryDTO.class);
        categoryDTO.setCategoryId(cateId);
        ProductCategoryRepository mockRepository = mock(ProductCategoryRepository.class);
        ProductCategoryDO dbCate = MockUtils.create(ProductCategoryDO.class);
        dbCate.setCategoryId(cateId);
        dbCate.setShopId(1L);
        when(mockRepository.findById(eq(cateId))).thenReturn(Optional.of(dbCate));

        ProductCategoryServiceImpl categoryService =
                new ProductCategoryServiceImpl(mockRepository, null, null, null, null, authApiClient, shopApiClient, null);
        categoryService.delete(token, cateId);
        verify(mockRepository).deleteById(eq(cateId));
    }
}