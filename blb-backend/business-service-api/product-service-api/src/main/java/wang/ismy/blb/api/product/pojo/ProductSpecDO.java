package wang.ismy.blb.api.product.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.common.BaseDO;

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
@Table(name="tb_product_spec")
public class ProductSpecDO extends BaseDO implements Serializable {
    /** spec */
    @Id
    private Long specId ;
    /** 规格所属商品 */
    private Long productId ;
    /** 规格名称 */
    private String specName ;
    /** 包装费 */
    private BigDecimal packageFee ;
    /** 价格 */
    private BigDecimal price ;
}
