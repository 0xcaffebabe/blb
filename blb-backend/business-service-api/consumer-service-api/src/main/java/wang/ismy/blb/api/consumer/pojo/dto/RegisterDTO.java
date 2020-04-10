package wang.ismy.blb.api.consumer.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MY
 * @date 2020/4/9 20:51
 */
@Data
@ApiModel("注册参数")
public class RegisterDTO {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("用户手机")
    private String phone;
    @ApiModelProperty("用户邮箱")
    private String email;
    @ApiModelProperty("登录密码")
    private String password;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("真实姓名")
    private String realName;
    @ApiModelProperty("性别")
    private Boolean gender;
}
