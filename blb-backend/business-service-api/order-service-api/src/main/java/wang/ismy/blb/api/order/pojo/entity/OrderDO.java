package wang.ismy.blb.api.order.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.common.BaseDO;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author MY
 * @date 2020/4/9 8:59
 */
@Table(name="tb_order")
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDO extends BaseDO implements Serializable {
    /** 订单ID */
    @Id
    private Long orderId ;
    /** 订单所属店铺 */
    private Long shopId ;
    /** 订餐者 */
    private Long consumerId ;
    /** 收货人地址信息 */
    private Long consumerDeliveryInfo ;
    /** 订单总金额 */
    private BigDecimal orderAmount ;
    /** 订单状态0：未处理，1：已处理，2：配送中，3：已完结，4：已作废 */
    private Integer orderStatus ;
    /** 支付状态，0为未支付，1为已支付，2为已取消 */
    private Integer payStatus ;
    /** 订单备注 */
    private String orderNote ;
    /** 订单详情列表 */
    private List<OrderDetailDO> orderDetailList;
}
