package wang.ismy.blb.api.consumer.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.common.BaseDO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author MY
 * @date 2020/4/9 20:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="tb_consumer_info")
@Entity
public class ConsumerInfoDO extends BaseDO implements Serializable {
    /**  */
    @Id
    private Long userId ;
    /** 用户头像图片地址 */
    private String avatar ;
    /** 用户的真实姓名 */
    private String realName ;
    /** 用户性别Y为男，N为女 */
    private Boolean gender ;
}
