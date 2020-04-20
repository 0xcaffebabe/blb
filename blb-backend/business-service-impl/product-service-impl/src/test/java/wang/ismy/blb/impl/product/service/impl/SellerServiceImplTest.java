package wang.ismy.blb.impl.product.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.auth.UserTypeEnum;
import wang.ismy.blb.api.product.pojo.ProductCategoryDO;
import wang.ismy.blb.api.product.pojo.ProductDO;
import wang.ismy.blb.api.product.pojo.dto.ProductCreateDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductSpecDTO;
import wang.ismy.blb.api.product.pojo.dto.ShopProductDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.common.BaseDO;
import wang.ismy.blb.common.SnowFlake;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.product.client.AuthApiClient;
import wang.ismy.blb.impl.product.client.ShopApiClient;
import wang.ismy.blb.impl.product.repository.ProductCategoryRepository;
import wang.ismy.blb.impl.product.repository.ProductRepository;
import wang.ismy.blb.impl.product.repository.ProductSpecRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class SellerServiceImplTest {

    @Autowired
    ProductCategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Test void testAddProduct(){
        String token = "token";
        ProductCreateDTO productCreateDTO = MockUtils.create(ProductCreateDTO.class);
        productCreateDTO.setProductSpecList(MockUtils.create(ProductSpecDTO.class,5));
        productCreateDTO.setProductCategory(1L);

        User user = new User();
        user.setUserId(1L);
        user.setUserType(UserTypeEnum.SELLER.getType());

        ShopInfoDTO shop = new ShopInfoDTO();
        shop.setShopId(1L);

        ProductCategoryRepository categoryRepository = mock(ProductCategoryRepository.class);
        when(categoryRepository.findById(eq(1L))).thenReturn(Optional.of(new ProductCategoryDO()));

        ProductRepository productRepository = mock(ProductRepository.class);
        ProductSpecRepository specRepository = mock(ProductSpecRepository.class);
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));

        ShopApiClient shopApiClient = mock(ShopApiClient.class);
        when(shopApiClient.getShopBySeller(eq(1L))).thenReturn(Result.success(shop));

        SnowFlake snowFlake = mock(SnowFlake.class);
        when(snowFlake.nextId()).thenReturn(1L);

        SellerServiceImpl sellerService = new SellerServiceImpl(categoryRepository, productRepository, specRepository, authApiClient, shopApiClient, snowFlake);
        sellerService.addProduct(token,productCreateDTO);
        verify(productRepository).save(any());
        verify(specRepository,times(5)).save(any());
    }

    @Test void testUpdateProduct(){
        String token = "token";
        Long productId = 1L;
        ProductCreateDTO productCreateDTO = MockUtils.create(ProductCreateDTO.class);
        productCreateDTO.setProductCategory(1L);

        User user = new User();
        user.setUserId(1L);
        user.setUserType(UserTypeEnum.SELLER.getType());

        ShopInfoDTO shop = new ShopInfoDTO();
        shop.setShopId(1L);

        ProductCategoryRepository categoryRepository = mock(ProductCategoryRepository.class);
        when(categoryRepository.findById(eq(1L))).thenReturn(Optional.of(new ProductCategoryDO()));

        ProductRepository productRepository = mock(ProductRepository.class);
        when(productRepository.findById(eq(productId))).thenReturn(Optional.of(new ProductDO()));

        ProductSpecRepository specRepository = mock(ProductSpecRepository.class);

        AuthApiClient authApiClient = mock(AuthApiClient.class);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));

        ShopApiClient shopApiClient = mock(ShopApiClient.class);
        when(shopApiClient.getShopBySeller(eq(1L))).thenReturn(Result.success(shop));

        SellerServiceImpl sellerService = new SellerServiceImpl(categoryRepository, productRepository, specRepository, authApiClient, shopApiClient, null);
        sellerService.updateProduct(token,productId,productCreateDTO);

        verify(productRepository).save(any());
    }

    @Test void testDelete(){
        String token = "token";
        Long productId = 1L;

        User user = new User();
        user.setUserId(1L);
        user.setUserType(UserTypeEnum.SELLER.getType());

        ShopInfoDTO shop = new ShopInfoDTO();
        shop.setShopId(1L);

        ProductCategoryDO category = new ProductCategoryDO();
        category.setShopId(1L);

        ProductDO product = new ProductDO();
        product.setProductCategory(1L);

        ProductCategoryRepository categoryRepository = mock(ProductCategoryRepository.class);
        when(categoryRepository.findById(eq(1L))).thenReturn(Optional.of(category));

        ProductRepository productRepository = mock(ProductRepository.class);
        when(productRepository.findById(eq(productId))).thenReturn(Optional.of(product));

        ProductSpecRepository specRepository = mock(ProductSpecRepository.class);

        AuthApiClient authApiClient = mock(AuthApiClient.class);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));

        ShopApiClient shopApiClient = mock(ShopApiClient.class);
        when(shopApiClient.getShopBySeller(eq(1L))).thenReturn(Result.success(shop));

        SellerServiceImpl sellerService = new SellerServiceImpl(categoryRepository, productRepository, specRepository, authApiClient, shopApiClient, null);
        sellerService.deleteProduct(token,productId);

        verify(productRepository).save(argThat(BaseDO::getRemoved));
    }

    @Test void testGetProductList(){
        String token = "token";
        Long productId = 1L;

        User user = new User();
        user.setUserId(1L);
        user.setUserType(UserTypeEnum.SELLER.getType());

        ShopInfoDTO shop = new ShopInfoDTO();
        shop.setShopId(1L);

        ProductCategoryDO category = new ProductCategoryDO();
        category.setShopId(1L);

        ProductDO product = new ProductDO();
        product.setProductCategory(1L);


        ProductSpecRepository specRepository = mock(ProductSpecRepository.class);

        AuthApiClient authApiClient = mock(AuthApiClient.class);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));

        ShopApiClient shopApiClient = mock(ShopApiClient.class);
        when(shopApiClient.getShopBySeller(eq(1L))).thenReturn(Result.success(shop));

        SellerServiceImpl sellerService = new SellerServiceImpl(categoryRepository, productRepository, specRepository, authApiClient, shopApiClient, null);
        var page = sellerService.getProductList(token, Pageable.of(1L, 5L));

        assertEquals(3,page.getTotal());
        assertEquals("黄焖鸡米饭",page.getData().get(0).getProductName());

    }

    @Test void testGetCategoryList(){
        String token = "token";

        User user = new User();
        user.setUserId(1L);
        user.setUserType(UserTypeEnum.SELLER.getType());

        ShopInfoDTO shop = new ShopInfoDTO();
        shop.setShopId(1L);

        ProductCategoryDO category = new ProductCategoryDO();
        category.setShopId(1L);

        ProductDO product = new ProductDO();
        product.setProductCategory(1L);


        ProductSpecRepository specRepository = mock(ProductSpecRepository.class);

        AuthApiClient authApiClient = mock(AuthApiClient.class);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));

        ShopApiClient shopApiClient = mock(ShopApiClient.class);
        when(shopApiClient.getShopBySeller(eq(1L))).thenReturn(Result.success(shop));

        SellerServiceImpl sellerService = new SellerServiceImpl(categoryRepository, productRepository, specRepository, authApiClient, shopApiClient, null);
        var list = sellerService.getCategoryList(token);

        assertEquals(2,list.size());
        assertEquals("1号店铺招牌菜",list.get(0).getCategoryName());
    }
}