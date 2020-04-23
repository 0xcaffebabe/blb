package wang.ismy.blb.api.shop.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MY
 * @date 2020/4/9 11:50
 */
@Data
@ApiModel("店铺目录")
public class ShopCategoryDTO {
    @ApiModelProperty("目录ID")
    private Long categoryId;
    @ApiModelProperty("父目录ID")
    private Long parentId;
    @ApiModelProperty("目录名称")
    private String categoryName;
    @ApiModelProperty("目录图片")
    private String categoryImg;
    private List<ShopCategoryDTO> subCategoryList;
    private Integer categoryLevel ;
    public ShopCategoryDTO append(ShopCategoryDTO item){
        if (CollectionUtils.isEmpty(subCategoryList)){
            subCategoryList = new ArrayList<>();
        }
        subCategoryList.add(item);
        return this;
    }
}
