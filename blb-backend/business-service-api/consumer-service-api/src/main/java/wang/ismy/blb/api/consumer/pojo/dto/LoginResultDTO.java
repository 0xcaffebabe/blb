package wang.ismy.blb.api.consumer.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MY
 * @date 2020/4/9 20:56
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
}
