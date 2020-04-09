package wang.ismy.blb.api.order.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MY
 * @date 2020/4/9 10:45
 */
@Data
@ApiModel("订单搜索条件")
public class OrderQuery {
    @ApiModelProperty("订单状态搜索条件")
    private String status;
    @ApiModelProperty("订单日期搜索条件")
    private String date;
}
