package wang.ismy.blb.api.consumer.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.common.BaseDO;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author MY
 * @date 2020/4/9 20:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="tb_delivery_info")
public class DeliveryInfoDO extends BaseDO implements Serializable {
    /** 收货信息ID */
    @Id
    private Long deliveryInfoId ;
    /** 收货地址具体小区 写字楼 学校等等 */
    private String building ;
    /** 详细地址 如门牌号 */
    private String detail ;
}
