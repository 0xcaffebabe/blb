package wang.ismy.blb.api.rider.pojo.dto.eval;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import wang.ismy.blb.api.rider.pojo.dto.OrderRiderDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author MY
 * @date 2020/4/8 14:00
 */
@Data
@ApiModel("骑手评价信息展示项")
public class RiderEvalItemDTO {
    @ApiModelProperty("评价者")
    private String consumer;
    @ApiModelProperty("评价分数")
    private BigDecimal ranking;
    @ApiModelProperty("评价内容")
    private String content;
    @ApiModelProperty("评价时间")
    private LocalDateTime createTime;
}
