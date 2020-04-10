package wang.ismy.blb.api.product.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/10 8:48
 */
@ApiModel("商品信息")
@Data
public class ProductCreateDTO {
    @ApiModelProperty("商品ID")
    private Long productId ;
    @ApiModelProperty("商品所属分类")
    private Long productCategory ;
    @ApiModelProperty("商品名称")
    private String productName ;
    @ApiModelProperty("商品详情")
    private String productDesc ;
    @ApiModelProperty("商品图片")
    private String productImg ;
    @ApiModelProperty("商品库存")
    private Long stock;
    @ApiModelProperty("商品规格列表")
    private List<ProductSpecDTO> productSpecList;
}
