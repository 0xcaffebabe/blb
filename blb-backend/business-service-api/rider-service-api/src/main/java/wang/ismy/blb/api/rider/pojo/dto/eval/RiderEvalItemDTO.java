package wang.ismy.blb.api.rider.pojo.dto.eval;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
