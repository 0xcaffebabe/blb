package wang.ismy.blb.api.location.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author MY
 * @date 2020/10/21 20:27
 */
@Data
@ApiModel("省份信息")
public class Province {
    @ApiModelProperty("省份名称")
    private String name;

    @ApiModelProperty("城市列表")
    private List<City> cityList;
}
