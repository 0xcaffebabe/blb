package wang.ismy.blb.api.cart;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author MY
 * @date 2020/4/10 10:57
 */
@Data
@ApiModel("购物车内容项")
public class CartItem {
    @ApiModelProperty("商品ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long productId;
    @ApiModelProperty("规格ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long specId;
    @ApiModelProperty("规格名称")
    private String specName;
    @ApiModelProperty("包装费")
    private BigDecimal packageFee;
    @ApiModelProperty("商品名称")
    private String productName;
    @ApiModelProperty("商品图片")
    private String productImg;
    @ApiModelProperty("商品数量")
    private Long productQuantity;
    @ApiModelProperty("商品单价")
    private BigDecimal productPrice;

    /**
     * 两个item只要product id 与spec id相同就视为相同
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItem cartItem = (CartItem) o;
        return productId.equals(cartItem.productId) &&
                specId.equals(cartItem.specId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, specId);
    }
}
