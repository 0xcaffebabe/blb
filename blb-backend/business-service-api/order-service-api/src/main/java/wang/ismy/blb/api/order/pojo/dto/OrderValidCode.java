package wang.ismy.blb.api.order.pojo.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MY
 * @date 2020/4/9 10:55
 */
@ApiModel("订单验证码")
@Data
public class OrderValidCode {

    /** 骑手取餐时向商家提供的验证码 */
    @ApiModelProperty("出餐验证码")
    private String diningOutCode;

    /** 订餐这取餐时向骑手提供的验证码 */
    @ApiModelProperty("取餐验证码")
    private String takeMealCode;
}
