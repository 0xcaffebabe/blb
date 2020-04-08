package wang.ismy.blb.api.rider.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.common.BaseDO;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author MY
 * @date 2020/4/8 11:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="tb_rider_order")
public class RiderOrderDO extends BaseDO implements Serializable {

    /** 订单ID */
    @Id
    private Long orderId ;
    /**  */
    private Long riderId ;
}
