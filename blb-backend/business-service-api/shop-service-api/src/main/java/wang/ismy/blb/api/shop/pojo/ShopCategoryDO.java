package wang.ismy.blb.api.shop.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.common.BaseDO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author MY
 * @date 2020/4/9 11:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="tb_shop_category")
@Entity
public class ShopCategoryDO extends BaseDO implements Serializable {
    /** 商铺目录ID */
    @Id
    private Long categoryId ;
    /** 父目录ID */
    private Long parentId ;
    /** 目录名称 */
    private String categoryName ;
    /** 目录图片地址 */
    private String categoryImg ;
    /** 目录层级(1级目录，2级目录...) */
    private Integer categoryLevel ;
}
