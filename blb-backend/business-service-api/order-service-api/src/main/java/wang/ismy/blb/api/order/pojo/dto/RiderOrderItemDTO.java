package wang.ismy.blb.api.order.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author MY
 * @date 2020/4/9 11:04
 */
@Data
@ApiModel("骑手可接订单列表项")
public class RiderOrderItemDTO {

    @ApiModelProperty("店铺logo")
    private String shopLogo;
    @ApiModelProperty("店铺名称")
    private String shopName;
    @ApiModelProperty("店铺地址")
    private String shopAddress;
    @ApiModelProperty("送货地址")
    private String targetAddress;
    @ApiModelProperty("订单号")
    private Long orderId;
    @ApiModelProperty("订单金额")
    private BigDecimal amount;
    @ApiModelProperty("店铺创建时间")
    private LocalDateTime createTime;

}
