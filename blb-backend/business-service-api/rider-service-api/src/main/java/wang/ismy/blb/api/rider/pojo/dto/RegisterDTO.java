package wang.ismy.blb.api.rider.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

/**
 * @author MY
 * @date 2020/4/8 11:43
 */
@Data
@ApiModel("骑手注册参数")
public class RegisterDTO {
    @ApiModelProperty(value = "骑手登录用户名",required = true)
    @NotEmpty(message = "骑手用户名不得为空")
    private String username;
    @ApiModelProperty("骑手登录手机")
    @NotEmpty(message = "骑手手机号码不得为空")
    private String phone;
    @NotEmpty(message = "骑手邮箱不得为空")
    @ApiModelProperty("骑手邮箱")
    private String email;
    @NotEmpty(message = "密码不得为空")
    @ApiModelProperty("骑手登录密码")
    private String password;
    @ApiModelProperty("骑手登录头像")
    private String avatar;
    @ApiModelProperty("骑手登录真实姓名")
    @NotEmpty(message = "骑手真实姓名不得为空")
    private String realName;
    @NotEmpty(message = "骑手身份证号码不得为空")
    @ApiModelProperty("骑手身份证号码")
    private String idNumber;
    @ApiModelProperty("骑手性别，true为男，false为女")
    private Boolean gender;
}
