package wang.ismy.blb.common;

import lombok.Data;
import lombok.ToString;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 所有DO的父类，带有DO的公共属性
 * @author MY
 * @date 2020/4/8 11:28
 */
@Data
@MappedSuperclass
public class BaseDO {

    /** 逻辑删除N为正常，Y为删除 */
    private Boolean removed ;
    /** 创建时间 */
    private LocalDateTime createTime ;
    /** 更新时间 */
    private LocalDateTime updateTime ;
}
