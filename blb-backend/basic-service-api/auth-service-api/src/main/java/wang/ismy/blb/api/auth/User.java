package wang.ismy.blb.api.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author MY
 * @date 2020/4/11 15:09
 */
@Data
@ApiModel("用户")
public class User {
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("用户类型")
    private String userType;
    @ApiModelProperty("一些附加信息")
    private Map<String,Object> addition;
}
