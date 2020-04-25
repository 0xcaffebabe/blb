package wang.ismy.blb.api.order.pojo.dto.consumer;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author MY
 * @date 2020/4/9 10:28
 */
@Data
@ApiModel("订餐者订单列表项")
public class ConsumerOrderItemDTO {

    @ApiModelProperty("店铺LOGO")
    private String shopLogo;
    @ApiModelProperty("订单号")
    private Long orderId;
    @ApiModelProperty("订单名称（使用订单信息生成）")
    private String orderName;
    @ApiModelProperty("订单创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @ApiModelProperty("支付金额")
    private BigDecimal orderAmount;
    @ApiModelProperty("订单状态")
    private Integer orderStatus;
    @ApiModelProperty("支付状态")
    private Integer payStatus;
}
