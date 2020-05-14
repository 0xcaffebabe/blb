package wang.ismy.blb.impl.cart.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.cache.CacheService;
import wang.ismy.blb.api.cart.CartItem;
import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductSpecDTO;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.impl.cart.CartConstant;
import wang.ismy.blb.impl.cart.client.AuthApiClient;
import wang.ismy.blb.impl.cart.client.ProductApiClient;
import wang.ismy.blb.impl.cart.dto.CartStoreDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
class CartServiceImplTest {

    @Test
    void testAddProduct(){

        String token = "token";
        Long productId = 1L;
        Long specId = 2L;

        ProductDTO product = new ProductDTO();
        product.setProductId(productId);
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
        productCategoryDTO.setShopId(1L);
        product.setProductCategory(productCategoryDTO);

        User user = new User();
        user.setUserId(1L);

        ProductSpecDTO spec = new ProductSpecDTO();
        spec.setSpecId(specId);
        spec.setPrice(new BigDecimal("25.0"));

        CacheService cacheService = mock(CacheService.class);
        ProductApiClient productApiClient = mock(ProductApiClient.class);
        AuthApiClient authApiClient = mock(AuthApiClient.class);

        when(productApiClient.getProduct(eq(productId))).thenReturn(Result.success(product));
        when(productApiClient.getProductSpec(eq(2L))).thenReturn(Result.success(spec));
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        when(cacheService.get(eq("1-1"),eq(CartStoreDTO.class))).thenReturn(null);

        CartServiceImpl cartService = new CartServiceImpl(cacheService,productApiClient,authApiClient);
        cartService.addProduct(token,productId,specId,2L);
        verify(cacheService).put(eq("1-1"),argThat(obj->{
            CartStoreDTO cart = (CartStoreDTO) obj;
            return cart.getConsumerId().equals(1L) && cart.getShopId().equals(1L)
                    && cart.getCartItemList().size() == 1
                    && cart.getCartItemList().get(0).getProductId().equals(1L)
                    && cart.getCartItemList().get(0).getSpecId().equals(2L)
                    && cart.getCartItemList().get(0).getProductPrice().equals(new BigDecimal("25.0"))
                    && cart.getCartItemList().get(0).getProductQuantity().equals(2L);
        }),eq(CartConstant.TTL));
    }

    @Test
    void testGetList(){
        String token = "token";
        Long shopId = 1L;

        User user = new User();
        user.setUserId(1L);

        CartStoreDTO cart = new CartStoreDTO();
        cart.append(new CartItem());

        CacheService cacheService = mock(CacheService.class);
        ProductApiClient productApiClient = mock(ProductApiClient.class);
        AuthApiClient authApiClient = mock(AuthApiClient.class);

        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        when(cacheService.get(eq("1-1"),eq(CartStoreDTO.class))).thenReturn(cart);

        CartServiceImpl cartService = new CartServiceImpl(cacheService,productApiClient,authApiClient);
        var list = cartService.getCartList(token,shopId);
        Assert.assertEquals(1,list.size());
    }

    @Test
    void testDeleteProduct(){
        String token = "token";
        Long productId = 1L;
        Long specId = 2L;

        ProductDTO product = new ProductDTO();
        product.setProductId(productId);
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
        productCategoryDTO.setShopId(1L);
        product.setProductCategory(productCategoryDTO);

        User user = new User();
        user.setUserId(1L);

        ProductSpecDTO spec = new ProductSpecDTO();
        spec.setSpecId(specId);
        spec.setPrice(new BigDecimal("25.0"));

        CartStoreDTO cart = new CartStoreDTO();
        List<CartItem> cartList = new ArrayList<>();
        CartItem item = new CartItem();
        item.setProductId(productId);
        item.setSpecId(specId);
        item.setProductQuantity(1L);
        cartList.add(item);
        cart.setCartItemList(cartList);

        CacheService cacheService = mock(CacheService.class);
        ProductApiClient productApiClient = mock(ProductApiClient.class);
        AuthApiClient authApiClient = mock(AuthApiClient.class);

        when(productApiClient.getProduct(eq(productId))).thenReturn(Result.success(product));
        when(productApiClient.getProductSpec(eq(2L))).thenReturn(Result.success(spec));
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        when(cacheService.get(eq("1-1"),eq(CartStoreDTO.class))).thenReturn(cart);

        CartServiceImpl cartService = new CartServiceImpl(cacheService,productApiClient,authApiClient);
        cartService.deleteProduct(token,productId,specId);
        verify(cacheService).put(eq("1-1"),argThat(obj->{
            CartStoreDTO c = (CartStoreDTO) obj;
            return c.getCartItemList().size() == 0;
        }),eq(CartConstant.TTL));
    }

    @Test
    void deleteProductList () {
        String token = "token";
        Long shopId = 1L;
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        User user = new User();
        user.setUserId(1L);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        CacheService cacheService = mock(CacheService.class);
        CartService cartService = new CartServiceImpl(cacheService,null,authApiClient);
        cartService.deleteProductList(token,shopId);
        verify(cacheService).delete(eq(shopId+"-"+user.getUserId()));
    }
}