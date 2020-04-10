package wang.ismy.blb.api.location;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import wang.ismy.blb.api.location.pojo.Location;
import wang.ismy.blb.common.Result;

import java.util.List;
import java.util.Map;

/**
 * @author MY
 * @date 2020/4/10 10:23
 */
@Api(tags = "位置服务接口")
public interface LocationApi {

    /**
     * 计算两点距离
     * @param locationList
     * @return
     */
    @ApiOperation("计算两点距离")
    @GetMapping("distance")
    Result<String> calcDistance(@RequestBody List<Location> locationList);

    /**
     * 根据地址计算距离
     * @param address
     * @param location
     * @return
     */
    @ApiOperation("根据地址计算距离")
    @GetMapping("distance/{address}")
    Result<String> calcDistance(@PathVariable("address") String address,
                                @RequestBody Location location);

    /**
     * 批量计算距离
     * @param locationMap 位置列表
     * @param location 被计算的距离
     * @return
     */
    @ApiOperation("批量计算距离")
    @GetMapping("distance/list/{location}")
    Result<List<Map<Long,String>>> calcDistanceBatch(@RequestBody Map<Long,Location> locationMap,
                                                     @PathVariable("location") String location);
}
