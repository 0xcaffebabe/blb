package wang.ismy.blb.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime ;
    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime ;

    public void initTime(){
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
}
