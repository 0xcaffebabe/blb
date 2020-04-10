package wang.ismy.blb.api.consumer.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MY
 * @date 2020/4/10 8:35
 */
@Data
@ApiModel("订餐者更新信息参数")
public class ConsumerUpdateDTO {
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("手机")
    private String phone;
}
