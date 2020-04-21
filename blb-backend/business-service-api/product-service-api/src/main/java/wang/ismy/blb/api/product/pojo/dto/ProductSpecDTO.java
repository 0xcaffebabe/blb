package wang.ismy.blb.api.product.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author MY
 * @date 2020/4/10 8:56
 */
@Data
@ApiModel("商品规格")
public class ProductSpecDTO {
    @ApiModelProperty("规格ID")
    private Long specId ;
    @ApiModelProperty("规格名称")
    private String specName ;
    @ApiModelProperty("包装费")
    private BigDecimal packageFee ;
    @ApiModelProperty("价格")
    private BigDecimal price ;
    @ApiModelProperty("库存")
    private Long stock;
}
