package wang.ismy.blb.api.product.pojo.dto.eval;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author MY
 * @date 2020/4/10 9:48
 */
@Data
@ApiModel("消费者评价项")
public class ConsumerEvalItem {
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("评价分数")
    private BigDecimal ranking;
    @ApiModelProperty("评价内容")
    private String content;
    @ApiModelProperty("评价时间")
    private LocalDateTime createTime;
}
