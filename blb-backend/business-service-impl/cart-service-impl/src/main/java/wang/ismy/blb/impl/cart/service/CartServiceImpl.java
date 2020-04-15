package wang.ismy.blb.impl.cart.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.cache.CacheService;
import wang.ismy.blb.api.cart.CartItem;
import wang.ismy.blb.api.product.pojo.dto.ProductDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductSpecDTO;
import wang.ismy.blb.common.BlbException;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.impl.cart.CartConstant;
import wang.ismy.blb.impl.cart.client.AuthApiClient;
import wang.ismy.blb.impl.cart.client.ProductApiClient;
import wang.ismy.blb.impl.cart.dto.CartStoreDTO;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/15 10:15
 */
@Service
@AllArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CacheService cacheService;
    private final ProductApiClient productApiClient;
    private final AuthApiClient authApiClient;

    @Override
    public void addProduct(String consumerToken, Long productId, Long specId, Long quantity) {
        ProductDTO product = getProduct(productId);
        if (product == null) {
            return;
        }

        User user = getUser(consumerToken);
        if (user == null) {
            return;
        }

        ProductSpecDTO spec = getSpec(specId);
        if (spec == null) {
            return;
        }

        String key = getKey(product, user);
        CartStoreDTO cart = cacheService.get(key, CartStoreDTO.class);
        // 如果购物车为空，就创建一个，否则直接追加
        if (cart == null) {
            cart = new CartStoreDTO();
            cart.setConsumerId(user.getUserId());
            cart.setShopId(product.getProductCategory().getShopId());
        }
        CartItem item = new CartItem();
        item.setProductId(productId);
        item.setProductImg(product.getProductImg());
        item.setProductName(product.getProductName());
        item.setProductPrice(spec.getPrice());
        item.setProductQuantity(quantity);
        item.setSpecId(specId);
        cart.append(item);
        // 写回缓存
        cacheService.put(key, cart, CartConstant.TTL);
    }

    @Override
    public List<CartItem> getCartList(String token, Long shopId) {
        var user = getUser(token);

        if (user == null) {
            throw new BlbException(ResultCode.USER_NOT_LOGGED_IN);
        }

        String key = shopId + "-" + user.getUserId();
        CartStoreDTO cart = cacheService.get(key, CartStoreDTO.class);
        if (cart == null) {
            return List.of();
        }
        if (CollectionUtils.isEmpty(cart.getCartItemList())) {
            return List.of();
        }
        return cart.getCartItemList();
    }

    @Override
    public void deleteProduct(String token, Long productId, Long specId) {
        ProductDTO product = getProduct(productId);
        if (product == null) {
            return;
        }
        User user = getUser(token);
        if (user == null) {
            return;
        }
        ProductSpecDTO spec = getSpec(specId);
        if (spec == null) {
            return;
        }

        String key = getKey(product,user);
        CartStoreDTO cart = cacheService.get(key, CartStoreDTO.class);
        if (cart == null) {
            return;
        }
        if (CollectionUtils.isEmpty(cart.getCartItemList())){
            return;
        }
        CartItem item = new CartItem();
        item.setProductId(productId);
        item.setSpecId(specId);
        cart.getCartItemList().remove(item);

        cacheService.put(key,cart,CartConstant.TTL);
    }

    private User getUser(String consumerToken) {
        Result<User> authRemoteResult = authApiClient.valid(consumerToken);
        if (!authRemoteResult.getSuccess()) {
            log.warn("调用认证服务获取用户信息失败，{}", authRemoteResult);
            return null;
        }
        var user = authRemoteResult.getData();

        if (user == null) {
            log.warn("购物车增加商品获取用户为空,{}", consumerToken);
            return null;
        }
        return user;
    }

    private ProductDTO getProduct(Long productId) {
        Result<ProductDTO> productRemoteResult = productApiClient.getProduct(productId);
        if (!productRemoteResult.getSuccess()) {
            log.warn("获取商品信息失败:{}", productRemoteResult);
            return null;
        }
        ProductDTO product = productRemoteResult.getData();
        if (product == null) {
            log.warn("商品信息不存在,{}", productId);
            return null;
        }
        return product;
    }

    private ProductSpecDTO getSpec(Long specId) {
        var specRemoteResult = productApiClient.getProductSpec(specId);
        if (!specRemoteResult.getSuccess()) {
            log.warn("获取商品规格信息失败,{}", specRemoteResult);
            return null;
        }
        var spec = specRemoteResult.getData();
        if (spec == null) {
            log.warn("购物车增加商品，获取商品规格为空:{}", specId);
            return null;
        }
        return spec;
    }

    private String getKey(ProductDTO product, User user) {
        return product.getProductCategory().getShopId() + "-" + user.getUserId();
    }
}
