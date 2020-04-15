package wang.ismy.blb.impl.cart;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.api.cart.CartApi;
import wang.ismy.blb.api.cart.CartItem;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.CurrentRequestUtils;
import wang.ismy.blb.impl.cart.service.CartService;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/15 8:40
 */
@RestController
@AllArgsConstructor
public class CartApiImpl implements CartApi {

    private final CartService cartService;
    @Override
    public Result<Void> addProduct(String token,Long productId, Long specId,Long quantity) {
        cartService.addProduct(token,productId,specId,quantity);
        return Result.success();
    }

    @Override
    public Result<List<CartItem>> getCartList(String token,Long shopId) {
        return Result.success(cartService.getCartList(token,shopId));
    }

    @Override
    public Result<Void> deleteProduct(String token,Long productId,Long specId) {
        cartService.deleteProduct(token,productId,specId);
        return Result.success();
    }
}
