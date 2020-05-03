package wang.ismy.blb.api.seller.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MY
 * @date 2020/4/10 10:21
 */
@Data
@ApiModel("登录结果")
public class LoginResultDTO {
    /** 登录成功会返回token */
    @ApiModelProperty(value = "登录凭证")
    private String token;
    /** 登录结果提示语 */
    @ApiModelProperty(value = "登录结果提示语")
    private String greeting;
    /** 0为登录成功 1为账号不存在 2为账户或密码错误*/
    private Integer status;
}
