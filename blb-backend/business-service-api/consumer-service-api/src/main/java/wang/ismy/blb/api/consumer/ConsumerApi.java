package wang.ismy.blb.api.consumer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.api.consumer.pojo.dto.*;
import wang.ismy.blb.common.result.Result;

import java.util.List;
import java.util.Map;

/**
 * @author MY
 * @date 2020/4/9 20:45
 */
@Api(tags = "消费者主服务接口")
public interface ConsumerApi {

    /**
     * 订餐者注册
     * @param registerDTO
     * @return 注册结果
     */
    @ApiOperation("订餐者注册")
    @PostMapping("register")
    Result<RegisterResultDTO> register(@RequestBody RegisterDTO registerDTO);

    /**
     * 订餐者登录接口
     *
     * @param loginStr 登录字符串（可以是用户名、邮箱、手机号）
     * @param password 登录密码
     * @return 登录结果，如果登录成功token会携带数据
     */
    @PostMapping("login")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "loginStr", dataType = "String", required = true, value = "登录字符串"),
            @ApiImplicitParam(paramType = "query", name = "password", dataType = "String", required = true, value = "登录密码")
    })
    @ApiOperation("订餐者登录接口")
    Result<LoginResultDTO> login(@RequestParam("loginStr") String loginStr,
                                 @RequestParam("password") String password);

    /**
     * 获取订餐者自己的信息
     * @return 消费者信息
     */
    @ApiOperation("获取订餐者自己的信息")
    @GetMapping("")
    Result<ConsumerDTO> getInfo();

    /**
     * 根据订餐者ID获取订餐者信息
     * @param consumerId
     * @return
     */
    @ApiOperation("根据订餐者ID获取订餐者信息")
    @ApiImplicitParam(paramType = "path", name = "consumerId", dataType = "Long", required = true, value = "订餐者ID")
    @GetMapping("{consumerId}")
    Result<ConsumerDTO> getInfo(@PathVariable String consumerId);


    /**
     * 根据订餐者ID批量获取订餐者信息
     * @param consumerIdList
     * @return
     */
    @ApiOperation("根据订餐者ID批量获取订餐者信息")
    @ApiImplicitParam(paramType = "query", name = "consumerIdList", dataType = "List", required = true, value = "订餐者ID列表")
    @GetMapping("list")
    Result<Map<Long,ConsumerDTO>> getInfo(@RequestParam("consumerIdList")List<Long> consumerIdList);

    /**
     * 修改订餐者自己的信息
     * @param consumerUpdateDTO
     * @return
     */
    @ApiOperation("修改订餐者自己的信息")
    @PutMapping("")
    Result<Void> updateInfo(@RequestBody ConsumerUpdateDTO consumerUpdateDTO);

    @ApiOperation("订餐者修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "oldPassword", dataType = "String", required = true, value = "旧密码"),
            @ApiImplicitParam(paramType = "query", name = "newPassword", dataType = "String", required = true, value = "新密码")
    })
    @PutMapping("password")
    Result<Void> updatePassword(@RequestParam("oldPassword") String oldPasword,
                                @RequestParam("newPassword") String newPassword);
}
