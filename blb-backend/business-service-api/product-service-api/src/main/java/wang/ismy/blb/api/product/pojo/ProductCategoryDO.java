package wang.ismy.blb.api.product.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.common.BaseDO;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author MY
 * @date 2020/4/10 8:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="tb_product_category")
public class ProductCategoryDO extends BaseDO implements Serializable {
    /** 商品分类ID */
    @Id
    private Long categoryId ;
    /** 商品分类所属店铺 */
    private Long shopId ;
    /** 目录名 */
    private String categoryName ;
    /** 目录描述 */
    private String categoryDesc ;
}
