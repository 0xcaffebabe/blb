package wang.ismy.blb.aggregation.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.api.cart.CartItem;

/**
 * @author MY
 * @date 2020/5/12 19:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CartItemShowDTO extends CartItem {
    private String specName;
}
