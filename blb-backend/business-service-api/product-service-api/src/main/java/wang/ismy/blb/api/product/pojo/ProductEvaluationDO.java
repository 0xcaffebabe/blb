package wang.ismy.blb.api.product.pojo;

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
 * @date 2020/4/10 8:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="tb_product_evaluation")
@Entity
public class ProductEvaluationDO extends BaseDO implements Serializable {
    /** 评价ID */
    @Id
    private Long evalId ;
    /** 评价商品ID */
    private Long productId ;
    /** 订餐者 */
    private Long consumerId ;
    /** 评价所属店铺 */
    private Long shopId ;
    /** 评价分数 */
    private BigDecimal ranking ;
    /** 评价内容 */
    private String content ;
}
