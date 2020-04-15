package wang.ismy.blb.impl.cart.dto;

import lombok.Data;
import org.springframework.util.CollectionUtils;
import wang.ismy.blb.api.cart.CartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 用来内部表示购物车信息
 *
 * @author MY
 * @date 2020/4/15 10:36
 */
@Data
public class CartStoreDTO {
    private Long consumerId;
    private Long shopId;
    private List<CartItem> cartItemList;

    public CartStoreDTO append(CartItem cartItem) {
        if (CollectionUtils.isEmpty(cartItemList)) {
            cartItemList = new ArrayList<>();
        }
        // 这个商品已经在购物车有了，直接修改数量
        int i = cartItemList.indexOf(cartItem);
        if (i != -1) {
            CartItem item = cartItemList.get(i);
            item.setProductQuantity(item.getProductQuantity() + item.getProductQuantity());
        } else {
            cartItemList.add(cartItem);
        }
        return this;
    }
}
