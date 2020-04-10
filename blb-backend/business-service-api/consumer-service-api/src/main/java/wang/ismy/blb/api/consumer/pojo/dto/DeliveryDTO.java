package wang.ismy.blb.api.consumer.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MY
 * @date 2020/4/9 20:59
 */
@Data
@ApiModel("收货信息参数")
public class DeliveryDTO {
    @ApiModelProperty("收货地址建筑物（小区、学校）")
    private String building;
    @ApiModelProperty("详细地址")
    private String detail;
    @ApiModelProperty("是否为默认地址")
    private Boolean isDefault;
}
