package wang.ismy.blb.api.shop.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.common.BaseDO;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author MY
 * @date 2020/4/9 11:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="tb_shop")
public class ShopDO extends BaseDO implements Serializable {
    /** 店铺ID */
    @Id
    private Long shopId ;
    /** 店铺所属商家ID */
    private Long sellerId ;
    /** 店铺所属分类 */
    private Long categoryId ;
    /** 店铺详细信息 */
    private ShopInfoDO shopInfo;
}
