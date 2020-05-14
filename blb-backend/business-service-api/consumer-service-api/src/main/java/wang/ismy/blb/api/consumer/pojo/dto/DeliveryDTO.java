package wang.ismy.blb.api.consumer.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

/**
 * @author MY
 * @date 2020/4/9 20:59
 */
@Data
@ApiModel("收货信息参数")
public class DeliveryDTO {
    @ApiModelProperty("收货信息ID")
    private String deliveryId;
    @ApiModelProperty("收货地址建筑物（小区、学校）")
    @NotEmpty
    @Max(value = 64,message = "建筑物长度不能超过64个字符")
    private String building;
    @ApiModelProperty("详细地址")
    @NotEmpty
    @Max(value = 100,message = "详细地址不能超过100个字符")
    private String detail;
    @ApiModelProperty("是否为默认地址")
    private Boolean defaultDelivery;
}
