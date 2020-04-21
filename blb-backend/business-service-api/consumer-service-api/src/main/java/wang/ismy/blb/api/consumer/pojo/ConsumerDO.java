package wang.ismy.blb.api.consumer.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.common.BaseDO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * @author MY
 * @date 2020/4/9 20:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="tb_consumer")
@Entity
public class ConsumerDO extends BaseDO implements Serializable {
    /** 用户ID */
    @Id
    private Long userId ;
    /** 用户名 */
    private String username ;
    /** 密码 */
    private String password ;
    /** 电子邮箱 */
    private String email ;
    /** 手机号码 */
    private String phone ;
    /** 账号是否可用 Y为可用 N为不可用 */
    private Boolean enable ;
    private transient ConsumerInfoDO consumerInfo;
    private transient List<DeliveryInfoDO> deliveryInfoList;
}
