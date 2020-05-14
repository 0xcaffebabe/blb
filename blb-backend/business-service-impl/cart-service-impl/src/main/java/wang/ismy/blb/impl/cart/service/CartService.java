package wang.ismy.blb.impl.cart.service;

import wang.ismy.blb.api.cart.CartItem;

import java.util.List;

/**
 * 定义购物车服务应该提供的接口
 * @author MY
 * @date 2020/4/15 8:41
 */
public interface CartService {
    /**
     * 往消费者在某个店铺的购物车加入商品
     * @param consumerToken
     * @param productId
     * @param specId
     * @param quantity
     */
    void addProduct(String consumerToken,Long productId, Long specId,Long quantity);

    /**
     * 消费者获取某个店铺的购物车
     * @param token 消费者token
     * @param shopId
     * @return
     */
    List<CartItem> getCartList(String token, Long shopId);


    /**
     * 删除购物车某个商品
     * @param token
     * @param productId
     * @param specId
     */
    void deleteProduct(String token, Long productId, Long specId);

    void deleteProductList(String token, Long shopId);
}
