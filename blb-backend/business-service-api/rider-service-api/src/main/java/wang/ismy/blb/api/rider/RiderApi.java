package wang.ismy.blb.api.rider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.api.rider.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.rider.pojo.dto.RegisterDTO;
import wang.ismy.blb.common.result.Result;

/**
 * @author MY
 * @date 2020/4/7 20:46
 */
@Api(tags = "骑手主服务接口")
public interface RiderApi {

    /**
     * 骑手注册接口
     *
     * @param registerDTO 注册传递参数
     * @return 注册结果
     */
    @PostMapping("register")
    @ApiOperation("骑手注册接口")
    Result<String> register(@RequestBody RegisterDTO registerDTO);

    /**
     * 骑手登录接口
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
    @ApiOperation("骑手登录接口")
    Result<LoginResultDTO> login(@RequestParam("loginStr") String loginStr,
                                 @RequestParam("password") String password);



}
