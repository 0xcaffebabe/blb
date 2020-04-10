package wang.ismy.blb.api.consumer.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MY
 * @date 2020/4/9 20:53
 */
@Data
@ApiModel("注册结果")
public class RegisterResultDTO {
    @ApiModelProperty("注册完成提示语")
    private String greeting;
    @ApiModelProperty("注册订单者数")
    private Long userNumber;
}
