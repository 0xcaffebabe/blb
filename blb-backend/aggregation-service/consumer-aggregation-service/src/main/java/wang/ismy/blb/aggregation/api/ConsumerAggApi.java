package wang.ismy.blb.aggregation.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.aggregation.client.ConsumerApiClient;
import wang.ismy.blb.api.consumer.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.consumer.pojo.dto.RegisterDTO;
import wang.ismy.blb.api.consumer.pojo.dto.RegisterResultDTO;
import wang.ismy.blb.common.result.Result;

/**
 * @author MY
 * @date 2020/5/7 8:59
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "消费者接口")
@AllArgsConstructor
public class ConsumerAggApi {

    private final ConsumerApiClient consumerApiClient;

    @ApiOperation("登录接口")
    @PostMapping("login")
    public Result<LoginResultDTO> login(String username,String password){
        return consumerApiClient.login(username,password);
    }

    @ApiOperation("订餐者注册")
    @PostMapping("register")
    public Result<RegisterResultDTO> register(@RequestBody RegisterDTO registerDTO){
        return consumerApiClient.register(registerDTO);
    }
}
