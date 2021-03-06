package wang.ismy.blb.aggregation.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wang.ismy.blb.aggregation.annotations.NeedLogin;
import wang.ismy.blb.aggregation.client.UploadApiClient;
import wang.ismy.blb.aggregation.client.consumer.ConsumerApiClient;
import wang.ismy.blb.api.consumer.pojo.dto.*;
import wang.ismy.blb.common.SystemConstant;
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
    private final UploadApiClient uploadApiClient;

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

    @ApiOperation("更新用户信息")
    @PostMapping("info")
    public Result<Void> updateConsumerInfo(@RequestBody ConsumerUpdateDTO consumerUpdateDTO){
        return consumerApiClient.updateInfo(consumerUpdateDTO);
    }

    @ApiOperation("修改密码")
    @PostMapping("info/password")
    public Result<Void> updatePassword(@RequestParam("oldPassword") String oldPassword,
                                       @RequestParam("newPassword") String newPassword){
        return consumerApiClient.updatePassword(oldPassword,newPassword);
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("info")
    public Result<ConsumerDTO> getInfo(){
        return consumerApiClient.getInfo();
    }

    /**
     * 消费者上传头像
     * @param file
     * @return
     */
    @ApiOperation(value = "消费者上传头像",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("avatar")
    @NeedLogin
    public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file){
        return uploadApiClient.upload(file);
    }
}
