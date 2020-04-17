package wang.ismy.blb.impl.product.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.common.BaseDO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author MY
 * @date 2020/4/17 14:16
 */
@Table(name="tb_product_stock")
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductStockDO extends BaseDO implements Serializable {
    /** 商品ID */
    @Id
    private Long productId ;
    /** 商品库存 */
    private Integer stock ;
}
