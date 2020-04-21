package wang.ismy.blb.api.consumer.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author MY
 * @date 2020/4/9 20:51
 */
@Data
@ApiModel("注册参数")
public class RegisterDTO {
    @ApiModelProperty("用户名")
    @NotEmpty(message = "用户名不得为空")
    private String username;
    @ApiModelProperty("用户手机")
    @NotEmpty(message = "手机不得为空")
    private String phone;
    @NotEmpty(message = "邮箱不得为空")
    @ApiModelProperty("用户邮箱")
    private String email;
    @NotEmpty(message = "登录密码不得为空")
    @ApiModelProperty("登录密码")
    private String password;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("真实姓名")
    private String realName;
    @ApiModelProperty("性别")
    private Boolean gender;
}
