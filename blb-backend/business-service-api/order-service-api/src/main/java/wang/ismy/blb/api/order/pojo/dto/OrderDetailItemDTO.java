package wang.ismy.blb.api.order.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author MY
 * @date 2020/4/9 10:00
 */
@Data
@ApiModel("订单详情DTO")
public class OrderDetailItemDTO {
    /** 订单详情ID */
    @ApiModelProperty("订单详情ID")
    private Long detailId ;
    /**  */
    @ApiModelProperty("订单ID")
    private Long orderId ;
    /** 订单商品ID */
    @ApiModelProperty("订单商品ID")
    private Long productId ;
    /** 商品名称 */
    @ApiModelProperty("商品名称")
    private String productName ;
    /** 商品图片地址 */
    @ApiModelProperty("商品图片地址")
    private String productImg ;
    /** 商品数量 */
    @ApiModelProperty("商品数量")
    private Integer productQuantity ;
    /** 购买时的商品单价 */
    @ApiModelProperty("购买时的商品单价")
    private BigDecimal productPrice ;
    /** 商品规格ID */
    @ApiModelProperty("商品规格ID")
    private Long productSpec ;
    private String specName;
}
