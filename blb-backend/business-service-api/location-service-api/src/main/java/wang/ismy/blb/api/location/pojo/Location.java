package wang.ismy.blb.api.location.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @author MY
 * @date 2020/4/10 10:24
 */
@Data
@ApiModel("位置")
public class Location {

    @ApiModelProperty("经度")
    private BigDecimal longitude;
    @ApiModelProperty("纬度")
    private BigDecimal latitude;

    /**
     * 将一个类似于"117.708708,24.052089"的字符串转为位置对象
     * @param str 经纬度字符串
     * @return 位置对象
     */
    public static Location from(String str) {
        String[] arr = str.split(",");
        if (arr.length != 2){
            throw new IllegalArgumentException("经纬度字符串不正确");
        }
        Location location = new Location();
        location.longitude = new BigDecimal(arr[0]);
        location.latitude = new BigDecimal(arr[1]);
        return location;
    }
}
