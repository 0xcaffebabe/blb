package wang.ismy.blb.api.shop.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MY
 * @date 2020/4/9 11:50
 */
@Data
@ApiModel("店铺目录")
public class ShopCategoryDTO {
    @ApiModelProperty("目录ID")
    private Long categoryId;
    @ApiModelProperty("目录名称")
    private String categoryName;
    @ApiModelProperty("目录图片")
    private String categoryImg;
}
