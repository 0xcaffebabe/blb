package wang.ismy.blb.common.result;

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
    /** 从1开始 */
    private Long page;
    @ApiModelProperty("每页展示数")
    private Long size;

    public static Pageable of(Long page,Long size){
        Pageable pageable = new Pageable();
        pageable.page = page;
        pageable.size = size;
        return pageable;
    }
}
