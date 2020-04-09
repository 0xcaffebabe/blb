package wang.ismy.blb.api.shop.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author MY
 * @date 2020/4/9 13:02
 */
@Data
@ApiModel("店铺列表展示项")
public class ShopItemDTO {
    @ApiModelProperty("店铺ID")
    private Long shopId;
    @ApiModelProperty("店铺LOGO")
    private String shopLogo;
    @ApiModelProperty("店铺名称")
    private String shopName;
    @ApiModelProperty("店铺评分")
    private BigDecimal ranking;
    @ApiModelProperty("店铺距离订餐者距离")
    private BigDecimal distance;
}
