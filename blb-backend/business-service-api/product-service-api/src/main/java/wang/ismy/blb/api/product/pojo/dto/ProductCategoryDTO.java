package wang.ismy.blb.api.product.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author MY
 * @date 2020/4/10 8:50
 */
@Data
@ApiModel("商品目录")
public class ProductCategoryDTO {
    @ApiModelProperty("目录ID")
    private Long categoryId;
    @ApiModelProperty("目录名称")
    @NotEmpty(message = "商品目录不能为空")
    private String categoryName;
    @ApiModelProperty("目录描述")
    private String categoryDesc;
    @ApiModelProperty("所属店铺")
    private Long shopId;
}
