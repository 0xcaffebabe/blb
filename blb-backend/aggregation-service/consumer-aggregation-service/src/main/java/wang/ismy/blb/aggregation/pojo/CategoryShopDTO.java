package wang.ismy.blb.aggregation.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.aspectj.weaver.ast.Or;
import wang.ismy.blb.api.shop.pojo.dto.ShopItemDTO;

import java.math.BigDecimal;

/**
 * @author MY
 * @date 2020/5/7 9:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryShopDTO extends ShopItemDTO {
    private Integer sales;
    private BigDecimal startingPrice;
    private BigDecimal deliveryFee;
    private String deliveryTime;
}
