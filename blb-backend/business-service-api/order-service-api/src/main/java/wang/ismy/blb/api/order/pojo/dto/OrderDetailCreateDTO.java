package wang.ismy.blb.api.order.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author MY
 * @date 2020/4/9 10:22
 */
@Data
@ApiModel("订单提交商品详情DTO")
public class OrderDetailCreateDTO {
    @ApiModelProperty("商品ID")
    @NotEmpty(message = "商品ID不得为空")
    private Long productId;
    @ApiModelProperty("商品规格ID")
    @NotEmpty(message = "规格ID不得为空")
    private Long specId;
    @ApiModelProperty("商品数量")
    @NotEmpty(message = "数量不得为空")
    private Integer quantity;
}
