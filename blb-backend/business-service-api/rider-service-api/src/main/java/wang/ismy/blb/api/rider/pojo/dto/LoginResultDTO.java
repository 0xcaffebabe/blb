package wang.ismy.blb.api.rider.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MY
 * @date 2020/4/8 13:03
 */
@Data
@ApiModel("骑手登录结果")
public class LoginResultDTO {
    /** 登录成功会返回token */
    @ApiModelProperty(value = "登录凭证")
    private String token;
    /** 登录结果提示语 */
    @ApiModelProperty(value = "登录结果提示语")
    private String greeting;
}
