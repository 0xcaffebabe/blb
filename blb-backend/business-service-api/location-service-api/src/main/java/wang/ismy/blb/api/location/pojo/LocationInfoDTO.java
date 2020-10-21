package wang.ismy.blb.api.location.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author MY
 * @date 2020/10/21 20:28
 */
@Data
@ApiModel("位置信息")
public class LocationInfoDTO {

    @ApiModelProperty("当前城市")
    private City currentCity;

    @ApiModelProperty("热门城市列表")
    private List<City> hostCityList;

    @ApiModelProperty("所有城市列表")
    private List<Province> allCityList;
}
