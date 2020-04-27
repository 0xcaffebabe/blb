package wang.ismy.blb.api.pay.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MY
 * @date 2020/4/27 8:47
 */
@Data
@ApiModel("支付状态")
public class PayStatusDTO {
    @ApiModelProperty("状态码")
    private Integer status;
    @ApiModelProperty("状态文本")
    private String msg;
}
