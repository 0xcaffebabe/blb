package wang.ismy.blb.api.product.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.common.BaseDO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author MY
 * @date 2020/4/10 8:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="tb_product")
@Entity
public class ProductDO extends BaseDO implements Serializable {
    /** 商品ID */
    @Id
    private Long productId ;
    /** 商品所属分类 */
    private Long productCategory ;
    /** 商品名称 */
    private String productName ;
    /** 商品详情 */
    private String productDesc ;
    /** 商品图片 */
    private String productImg ;
    /** 商品库存 */
    private transient Long stock;
}
