package wang.ismy.blb.api.consumer;

import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wang.ismy.blb.api.consumer.pojo.dto.*;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.result.Result;

import java.util.List;
import java.util.Map;

/**
 * @author MY
 * @date 2020/4/9 20:45
 */
@Api(tags = "消费者主服务接口")
@RequestMapping(value = "v1/api",produces = MediaType.APPLICATION_JSON_VALUE)
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
    Result<ConsumerDTO> getInfo(@PathVariable Long consumerId);


    /**
     * 根据订餐者ID批量获取订餐者信息
     * @param consumerIdList
     * @return
     */
    @ApiOperation("根据订餐者ID批量获取订餐者信息")
    @GetMapping("list")
    Result<Map<Long,ConsumerDTO>> getInfo(@RequestParam("consumerIdList") @ApiParam("consumerIdList") List<Long> consumerIdList);

    /**
     * 修改订餐者自己的信息
     * @param consumerUpdateDTO
     * @return
     */
    @ApiOperation("修改订餐者自己的信息")
    @PutMapping("")
    Result<Void> updateInfo(@RequestBody ConsumerUpdateDTO consumerUpdateDTO);

    /**
     * 消费者修改密码
     * @param oldPasword
     * @param newPassword
     * @return
     */
    @ApiOperation("订餐者修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "oldPassword", dataType = "String", required = true, value = "旧密码"),
            @ApiImplicitParam(paramType = "query", name = "newPassword", dataType = "String", required = true, value = "新密码")
    })
    @PutMapping("password")
    Result<Void> updatePassword(@RequestParam("oldPassword") String oldPasword,
                                @RequestParam("newPassword") String newPassword);

    class Fallback implements ConsumerApi{

        @Override
        public Result<RegisterResultDTO> register(RegisterDTO registerDTO) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 消费者服务 注册接口失败");
        }

        @Override
        public Result<LoginResultDTO> login(String loginStr, String password) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 消费者服务 登录接口失败");
        }

        @Override
        public Result<ConsumerDTO> getInfo() {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 消费者服务 获取消费者信息接口失败");
        }

        @Override
        public Result<ConsumerDTO> getInfo(Long consumerId) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 消费者服务 获取消费者信息接口失败");
        }

        @Override
        public Result<Map<Long, ConsumerDTO>> getInfo(List<Long> consumerIdList) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 消费者服务 获取消费者信息接口失败");
        }

        @Override
        public Result<Void> updateInfo(ConsumerUpdateDTO consumerUpdateDTO) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 消费者服务 更新消费者信息接口失败");
        }

        @Override
        public Result<Void> updatePassword(String oldPasword, String newPassword) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 消费者服务 更新消费者密码接口失败");
        }
    }
}
