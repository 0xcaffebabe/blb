package wang.ismy.blb.api.seller.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.common.BaseDO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author MY
 * @date 2020/4/10 10:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="tb_seller")
@Entity
public class SellerDO extends BaseDO implements Serializable {
    /** 商家ID */
    @Id
    private Long sellerId ;
    /**  */
    private String username ;
    /**  */
    private String phone ;
    /**  */
    private String email ;
    /**  */
    private String password ;
    /** 账户是否可用 */
    private Boolean enable ;
}
