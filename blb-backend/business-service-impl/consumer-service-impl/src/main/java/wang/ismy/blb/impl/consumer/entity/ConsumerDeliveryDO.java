package wang.ismy.blb.impl.consumer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.common.BaseDO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author MY
 * @date 2020/4/22 9:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="tb_consumer_delivery")
@Entity
public class ConsumerDeliveryDO extends BaseDO implements Serializable {
    @Id
    private Long deliveryInfoId ;
    /**  */
    private Long userId ;
    /** 是否是默认收货信息 */
    private Boolean defaultDelivery ;
}
