package wang.ismy.blb.api.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.result.Result;

import javax.validation.Valid;

/**
 * @author MY
 * @date 2020/4/11 15:09
 */
@Api(tags = "认证服务接口")
@RequestMapping("v1/api")
public interface AuthApi {

    /**
     * 认证服务会返回一个全局唯一的token
     * @param user
     * @return
     */
    @ApiOperation("认证")
    @PutMapping(value = "",consumes = MediaType.APPLICATION_JSON_VALUE)
    Result<String> auth(@RequestBody @Valid User user);

    /**
     * 鉴权
     * @param token
     * @return
     */
    @ApiOperation("鉴权")
    @ApiImplicitParam(paramType = "path", name = "token", dataType = "String", required = true, value = "令牌")
    @GetMapping("{token}")
    Result<User> valid(@PathVariable("token") String token);

    class Fallback implements AuthApi{

        @Override
        public Result<String> auth(@Valid User user) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用认证服务认证接口失败");
        }

        @Override
        public Result<User> valid(String token) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用认证服务鉴权接口失败");
        }
    }
}
