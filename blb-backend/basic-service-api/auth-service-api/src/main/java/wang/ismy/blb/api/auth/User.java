package wang.ismy.blb.api.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author MY
 * @date 2020/4/11 15:09
 */
@Data
@ApiModel("用户")
@EqualsAndHashCode
public class User {
    @ApiModelProperty("用户名")
    @NotEmpty(message = "用户名不得为空")
    private String username;
    @NotNull(message = "用户ID不得为空")
    @ApiModelProperty("用户ID")
    private Long userId;
    @NotEmpty(message = "用户类型不得为空")
    @ApiModelProperty("用户类型")
    private String userType;
    @ApiModelProperty("一些附加信息")
    private Map<String,Object> addition;
}
