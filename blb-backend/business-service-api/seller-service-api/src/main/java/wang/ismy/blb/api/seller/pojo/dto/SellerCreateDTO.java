package wang.ismy.blb.api.seller.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

/**
 * @author MY
 * @date 2020/4/10 10:16
 */
@Data
@ApiModel("商家注册参数")
public class SellerCreateDTO {
    @ApiModelProperty("用户名")
    @NotEmpty(message = "用户名不得为空")
    private String username;
    @ApiModelProperty("密码")
    @NotEmpty(message = "密码不得为空")
    private String password;
}
