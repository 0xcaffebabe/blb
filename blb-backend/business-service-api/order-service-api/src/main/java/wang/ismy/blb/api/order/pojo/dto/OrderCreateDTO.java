package wang.ismy.blb.api.order.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/9 10:21
 */
@Data
@ApiModel("订单创建DTO")
public class OrderCreateDTO {
    @ApiModelProperty("商品列表")
    private List<OrderDetailCreateDTO> productList;

    @ApiModelProperty("收货信息ID")
    private Long deliveryId;

    @ApiModelProperty("订单备注")
    private String orderNote;
}
