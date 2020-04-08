package wang.ismy.blb.api.rider.pojo.dto.eval;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 骑手评价信息展示DTO
 * @author MY
 * @date 2020/4/8 13:48
 */
@Data
@ApiModel("骑手评价信息")
public class RiderEvalInfoDTO {
    @ApiModelProperty("骑手综合分数")
    private BigDecimal ranking;
    @ApiModelProperty("评价词云")
    private List<String> wordCloud;
}
