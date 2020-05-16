package wang.ismy.blb.api.order.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.common.BaseDO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author MY
 * @date 2020/4/9 9:02
 */
@Table(name="tb_order_detail")
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class OrderDetailDO extends BaseDO implements Serializable {
    /** 订单详情ID */
    @Id
    private Long detailId ;
    /**  */
    private Long orderId ;
    /** 订单商品ID */
    private Long productId ;
    /** 商品名称 */
    private String productName ;
    private String specName;
    /** 商品图片地址 */
    private String productImg ;
    /** 商品数量 */
    private Integer productQuantity ;
    /** 购买时的商品单价 */
    private BigDecimal productPrice ;
    /** 商品规格 */
    private Long productSpec ;
}
