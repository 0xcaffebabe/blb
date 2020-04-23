package wang.ismy.blb.impl.shop.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.seller.SellerApi;
import wang.ismy.blb.api.seller.pojo.SellerInfoDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopCreateDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoUpdateDTO;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.shop.client.AuthApiClient;
import wang.ismy.blb.impl.shop.client.ProductEvalApiClient;
import wang.ismy.blb.impl.shop.client.SellerApiClient;
import wang.ismy.blb.impl.shop.repository.ShopInfoRepository;
import wang.ismy.blb.impl.shop.repository.ShopRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class ShopServiceImplTest {

    @Autowired
    ShopServiceImpl shopService;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    ShopInfoRepository shopInfoRepository;

    @Test
    void getNearbyShop() {
        ProductEvalApiClient evalApiClient = mock(ProductEvalApiClient.class);
        var map = Map.of(1L,new BigDecimal("3.5"),2L,new BigDecimal("3.5"));
        when(evalApiClient.getShopEval(anyList())).thenReturn(Result.success(map));
        shopService.setProductEvalApiClient(evalApiClient);

        var page = shopService.getNearbyShop("117,29", Pageable.of(1L,5L));
        assertEquals(2,page.getTotal());
        assertEquals("黄焖鸡米饭",page.getData().get(0).getShopName());
        assertEquals(new BigDecimal("3.5"),page.getData().get(0).getRanking());
        assertEquals("阿牛很芒",page.getData().get(1).getShopName());
        assertEquals(new BigDecimal("3.5"),page.getData().get(1).getRanking());
    }

    @Test
    void getShopInfo() {
        Long shopId = 1L;
        SellerApiClient sellerApiClient = mock(SellerApiClient.class);
        SellerInfoDTO sellerInfoDTO = new SellerInfoDTO();
        sellerInfoDTO.setRealName("蔡徐坤");
        sellerInfoDTO.setPhone("1234567");

        when(sellerApiClient.getSellerInfo(eq(1L))).thenReturn(Result.success(sellerInfoDTO));
        shopService.setSellerApiClient(sellerApiClient);

        ShopInfoDTO shopInfo = shopService.getShopInfo(shopId);
        assertEquals("黄焖鸡米饭",shopInfo.getShopName());
        assertEquals("蔡徐坤",shopInfo.getSellerName());
    }

    @Test
    void testGetShopInfo() {
        var idList = List.of(1L,2L);
        var map = shopService.getShopInfo(idList);
        assertEquals(2,map.size());
        assertEquals("黄焖鸡米饭",map.get(1L).getShopName());
        assertEquals("阿牛很芒",map.get(2L).getShopName());
    }

    @Test
    @Transactional
    void addShop() {
        String token = "token";
        ShopCreateDTO createDTO = MockUtils.create(ShopCreateDTO.class);
        createDTO.setCategoryId(2L);
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        User user = new User();
        user.setUserId(3L);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        shopService.setAuthApiClient(authApiClient);

        Long shopId = shopService.addShop(token, createDTO);
        var shop = shopRepository.findById(shopId).orElse(null);
        var shopInfo = shopInfoRepository.findById(shopId).orElse(null);

        assertEquals(createDTO.getShopName(),shopInfo.getShopName());
        assertEquals(createDTO.getCategoryId(),shop.getCategoryId());
    }

    @Test
    @Transactional
    void updateShopInfo() {
        String token = "token";
        Long shopId = 1L;
        ShopInfoUpdateDTO updateDTO = MockUtils.create(ShopInfoUpdateDTO.class);
        updateDTO.setCategoryId(1L);
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        User user = new User();
        user.setUserId(1L);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        shopService.setAuthApiClient(authApiClient);

        shopService.updateShopInfo(token, shopId,updateDTO);
        var shop = shopRepository.findById(shopId).orElse(null);
        var shopInfo = shopInfoRepository.findById(shopId).orElse(null);

        assertEquals(updateDTO.getShopName(),shopInfo.getShopName());
        assertEquals(updateDTO.getCategoryId(),shop.getCategoryId());
    }

    @Test
    void getShopBySeller() {
        Long sellerId = 1L;
        SellerApiClient sellerApiClient = mock(SellerApiClient.class);
        SellerInfoDTO sellerInfoDTO = new SellerInfoDTO();
        sellerInfoDTO.setRealName("蔡徐坤");
        sellerInfoDTO.setPhone("1234567");

        when(sellerApiClient.getSellerInfo(eq(1L))).thenReturn(Result.success(sellerInfoDTO));
        shopService.setSellerApiClient(sellerApiClient);

        ShopInfoDTO shopInfo = shopService.getShopBySeller(sellerId);
        assertEquals("黄焖鸡米饭",shopInfo.getShopName());
        assertEquals("蔡徐坤",shopInfo.getSellerName());
    }
}