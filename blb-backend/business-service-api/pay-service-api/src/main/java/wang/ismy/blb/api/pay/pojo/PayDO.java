package wang.ismy.blb.api.pay.pojo;

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
 * @date 2020/4/9 11:19
 */
@EqualsAndHashCode(callSuper = true)
@Table(name="tb_pay")
@Data
@Entity
public class PayDO extends BaseDO implements Serializable {

    /** 支付ID */
    @Id
    private Long payId ;
    /** 第三方支付系统ID */
    private String thirdPartId ;
    /** 支付类型 */
    private String payType ;
    /** 所属订单 */
    private Long orderId ;
    /** 所属订餐者 */
    private Long consumerId ;
    /** 实际支付金额 */
    private BigDecimal payAmount ;
    /** 支付状态，0为未支付，1为已支付，2为已取消 */
    private Integer payStatus ;

    private String payTitle;
}
