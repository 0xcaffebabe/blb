package wang.ismy.blb.api.order.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MY
 * @date 2020/4/9 10:22
 */
@Data
@ApiModel("订单提交商品详情DTO")
public class OrderDetailCreateDTO {
    @ApiModelProperty("商品ID")
    private Long productId;
    @ApiModelProperty("商品规格ID")
    private Long specId;
    @ApiModelProperty("商品数量")
    private Integer quantity;
}
