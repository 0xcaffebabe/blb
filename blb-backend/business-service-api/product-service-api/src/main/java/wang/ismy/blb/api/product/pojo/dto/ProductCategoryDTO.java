package wang.ismy.blb.api.product.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    private String categoryName;
    @ApiModelProperty("目录描述")
    private String categoryDesc;
}
