package wang.ismy.blb.api.pay.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MY
 * @date 2020/10/19 19:56
 */
@Data
@ApiModel("支付信息")
public class PayInfoDTO {
    @ApiModelProperty("二维码链接")
    private String url;

    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty("所属订单ID")
    private Long orderId;
}
