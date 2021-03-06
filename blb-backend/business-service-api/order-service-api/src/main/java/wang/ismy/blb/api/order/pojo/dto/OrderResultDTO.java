package wang.ismy.blb.api.order.pojo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author MY
 * @date 2020/4/9 9:22
 */
@Data
@ApiModel("订单展示结果DTO")
public class OrderResultDTO {
    @ApiModelProperty("订单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId ;
    @ApiModelProperty("订单所属店铺")
    private Long shopId ;
    @ApiModelProperty("订餐者")
    private Long consumerId ;
    /** 收货人 */
    private String consumerName ;
    /** 收货人手机 */
    private String consumerPhone ;
    /** 收货地址 */
    private String consumerAddress ;
    @ApiModelProperty("订单总金额")
    private BigDecimal orderAmount ;
    @ApiModelProperty("订单状态")
    private Integer orderStatus ;
    @ApiModelProperty("订单支付状态")
    private Integer payStatus ;
    @ApiModelProperty("订单备注")
    private String orderNote ;
    @ApiModelProperty("订单详情列表")
    private List<OrderDetailItemDTO> orderDetailList;
}
