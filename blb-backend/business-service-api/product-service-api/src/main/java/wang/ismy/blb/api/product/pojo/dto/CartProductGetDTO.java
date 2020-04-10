package wang.ismy.blb.api.product.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MY
 * @date 2020/4/10 9:08
 */
@Data
@ApiModel("商品ID与规格ID")
public class CartProductGetDTO {
    @ApiModelProperty("商品ID")
    private Long productId;
    @ApiModelProperty("规格ID")
    private Long specId;
}
