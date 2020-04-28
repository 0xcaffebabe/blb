package wang.ismy.blb.impl.rider.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.common.BaseDO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author MY
 * @date 2020/4/28 10:02
 */
@Table(name="tb_rider_order")
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class RiderOrderDO extends BaseDO implements Serializable {
    @Id
    private Long orderId;
    private Long riderId;
}
