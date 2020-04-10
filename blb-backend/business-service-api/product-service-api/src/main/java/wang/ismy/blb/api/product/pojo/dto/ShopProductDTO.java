package wang.ismy.blb.api.product.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author MY
 * @date 2020/4/10 9:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("在店铺内展示的商品信息")
public class ShopProductDTO extends ProductDTO {

    @ApiModelProperty("月销量")
    private Long sales;
    @ApiModelProperty("好评率")
    private BigDecimal positiveRate;
}
