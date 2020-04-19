package wang.ismy.blb.common.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果展示DTO
 * @author MY
 * @date 2020/4/8 13:57
 */
@Data
@ApiModel("分页结果展示")
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {

    @ApiModelProperty("结果总数")
    private Long total;
    @ApiModelProperty("当前页展示结果")
    private List<T> data;
}
