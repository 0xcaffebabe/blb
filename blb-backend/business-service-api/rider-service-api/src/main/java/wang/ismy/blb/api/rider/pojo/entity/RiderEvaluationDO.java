package wang.ismy.blb.api.rider.pojo.entity;

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
 * @date 2020/4/8 11:38
 */
@Table(name="tb_rider_evaluation")
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class RiderEvaluationDO extends BaseDO implements Serializable {
    /** 骑手评价ID */
    @Id
    private Long evalId ;
    /** 该评价所属订单 */
    private Long orderId ;
    /** 骑手ID */
    private Long riderId ;
    private Long consumerId;
    /** 本次评价评分 */
    private BigDecimal ranking ;
    /** 评价内容 */
    private String content ;
}
