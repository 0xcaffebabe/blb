package wang.ismy.blb.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分页结果展示DTO
 * @author MY
 * @date 2020/4/8 13:57
 */
@Data
@ApiModel("分页结果展示")
public class Page<T> {

    @ApiModelProperty("结果总数")
    private Long total;
    @ApiModelProperty("当前页展示结果")
    private List<T> data;
}
