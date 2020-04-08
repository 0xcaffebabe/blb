package wang.ismy.blb.api.rider.pojo.dto.eval;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单
 * @author MY
 * @date 2020/4/8 13:28
 */
@Data
@ApiModel("骑手评分参数")
public class RiderEvalSubmitDTO {
    @ApiModelProperty("订单ID")
    private Long orderId;
    /** 对骑手的评分 */
    @ApiModelProperty("对骑手的评分")
    private BigDecimal ranking;
    @ApiModelProperty("评价内容")
    private String content;
}
