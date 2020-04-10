package wang.ismy.blb.api.seller.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.common.BaseDO;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author MY
 * @date 2020/4/10 10:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="tb_seller_info")
public class SellerInfoDO extends BaseDO implements Serializable {
    /**  */
    @Id
    private Long sellerId ;
    /** 联系电话 */
    private String concatNumber ;
    /** 商家真实姓名 */
    private String realName ;
    /** 商家身份证号 */
    private String idNumber ;
}
