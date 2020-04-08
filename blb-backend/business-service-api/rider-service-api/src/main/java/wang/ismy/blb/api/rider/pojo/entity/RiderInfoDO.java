package wang.ismy.blb.api.rider.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.common.BaseDO;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author MY
 * @date 2020/4/8 11:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="tb_rider_info")
public class RiderInfoDO extends BaseDO implements Serializable {
    /** 骑手唯一ID */
    @Id
    private Long riderId ;
    /** 骑手头像 */
    private String avatar ;
    /** 骑手真实姓名 */
    private String realName ;
    /** 骑手身份证号 */
    private String idNumber ;
    /** 骑手性别T为男，F为女 */
    private Boolean gender ;
}
