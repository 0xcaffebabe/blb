package wang.ismy.blb.api.rider.pojo.dto.eval;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 骑手评价结果
 * @author MY
 * @date 2020/4/8 13:37
 */
@Data
@ApiModel("骑手评价结果")
public class RiderEvalResultDTO {
    /** 评价成功后骑手平均分 */
    @ApiModelProperty("骑手当前评价")
    private BigDecimal avgRanking;
    /** 这条评价是第几条评价*/
    @ApiModelProperty("骑手当前评价数")
    private Long evalNumber;
}
