package wang.ismy.blb.api.consumer.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MY
 * @date 2020/4/9 21:13
 */
@Data
@ApiModel("用户信息")
public class ConsumerDTO {
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("真实姓名")
    private String realName;
    @ApiModelProperty("手机")
    private String phone;
    @ApiModelProperty("电子邮箱")
    private String email;
}
