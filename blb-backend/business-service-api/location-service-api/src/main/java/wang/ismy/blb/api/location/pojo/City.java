package wang.ismy.blb.api.location.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MY
 * @date 2020/10/21 20:25
 */
@Data
@ApiModel("城市信息")
public class City {
    @ApiModelProperty("城市名称")
    private String name;

    @ApiModelProperty("城市经纬度")
    private String lonLat;
}
