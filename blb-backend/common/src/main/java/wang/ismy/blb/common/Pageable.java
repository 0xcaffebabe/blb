package wang.ismy.blb.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页DTO
 * @author MY
 * @date 2020/4/8 13:54
 */
@Data
@ApiModel("分页信息")
public class Pageable {
    @ApiModelProperty("页码")
    private Long page;
    @ApiModelProperty("每页展示数")
    private Long size;
}
