package wang.ismy.blb.api.order.pojo.dto.consumer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.api.order.pojo.dto.OrderDetailItemDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author MY
 * @date 2020/4/9 10:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("消费者订单详细信息")
public class ConsumerOrderDetailDTO extends ConsumerOrderItemDTO {
    @ApiModelProperty("订单商品信息")
    private List<OrderDetailItemDTO> productList;

    @ApiModelProperty("订单备注")
    private String orderNote;

    @ApiModelProperty("收货地址")
    private String address;
}
