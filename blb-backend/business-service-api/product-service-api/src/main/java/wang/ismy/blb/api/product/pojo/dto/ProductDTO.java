package wang.ismy.blb.api.product.pojo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MY
 * @date 2020/4/10 8:48
 */
@ApiModel("商品信息")
@Data
public class ProductDTO {
    @ApiModelProperty("商品ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long productId ;
    @ApiModelProperty("商品所属分类")
    private ProductCategoryDTO productCategory ;
    @ApiModelProperty("商品名称")
    private String productName ;
    @ApiModelProperty("商品详情")
    private String productDesc ;
    @ApiModelProperty("商品图片")
    private String productImg ;
    @ApiModelProperty("商品规格列表")
    private List<ProductSpecDTO> productSpecList;

    public ProductDTO appendSpec(ProductSpecDTO spec){
        if (productSpecList == null){
            productSpecList = new ArrayList<>();
        }
        productSpecList.add(spec);
        return this;
    }
}
