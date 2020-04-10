package wang.ismy.blb.api.product.pojo.dto.eval;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author MY
 * @date 2020/4/10 9:52
 */
@Data
@ApiModel("添加评价参数")
public class EvalCreateDTO {
    @ApiModelProperty("订单ID")
    private Long orderId;
    @ApiModelProperty("评价分数")
    private BigDecimal ranking;
    @ApiModelProperty("评价内容")
    private String content;
}
