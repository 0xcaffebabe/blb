package wang.ismy.blb.api.rider.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.common.BaseDO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 骑手实体
 * @author MY
 * @date 2020/4/8 11:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="tb_rider")
@Entity
public class RiderDO  extends BaseDO implements Serializable {
    /** 骑手ID */
    @Id
    private Long riderId ;
    /** 骑手用户名 */
    private String username ;
    /** 手机号码 */
    private String phone ;
    /** 电子邮箱 */
    private String email ;
    /** 登录密码 */
    private String password ;
    /** 账户状态Y为正常N为不可用 */
    private Boolean enable ;
    /** 骑手详细信息 */
    private transient RiderInfoDO riderInfo;
}
