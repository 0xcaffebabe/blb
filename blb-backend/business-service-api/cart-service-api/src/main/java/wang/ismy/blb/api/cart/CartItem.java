package wang.ismy.blb.api.cart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author MY
 * @date 2020/4/10 10:57
 */
@Data
@ApiModel("购物车内容项")
public class CartItem {
    @ApiModelProperty("商品ID")
    private Long productId;
    @ApiModelProperty("规格ID")
    private Long specId;
    @ApiModelProperty("商品名称")
    private String productName;
    @ApiModelProperty("商品图片")
    private String productImg;
    @ApiModelProperty("商品数量")
    private Long productQuantity;
    @ApiModelProperty("商品单价")
    private BigDecimal productPrice;
}
