package wang.ismy.blb.aggregation.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.aggregation.client.ConsumerApiClient;
import wang.ismy.blb.api.consumer.pojo.dto.ConsumerDTO;
import wang.ismy.blb.common.result.Result;

/**
 * @author MY
 * @date 2020/5/4 8:57
 */
@RestController
@RequestMapping(value = "consumer",produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "消费者接口")
@AllArgsConstructor
public class TestApi {
    private ConsumerApiClient consumerApiClient;
    @GetMapping("info/{name}")
    @ApiOperation("获取消费者信息")
    public Result<ConsumerDTO> info(@PathVariable String name) {
        return consumerApiClient.getInfo(1L);
    }
}
