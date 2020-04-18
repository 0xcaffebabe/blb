package wang.ismy.blb.impl.auth;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.api.auth.AuthApi;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.impl.auth.service.AuthService;

/**
 * @author MY
 * @date 2020/4/13 8:45
 */
@RestController
@AllArgsConstructor
public class AuthApiImpl implements AuthApi {

    private AuthService authService;
    @Override
    public Result<String> auth(User user) {
        String token = authService.auth(user);
        if (StringUtils.isEmpty(token)){
            return Result.failure(ResultCode.AUTH_ERROR);
        }
        return Result.success(token);
    }

    @Override
    public Result<User> valid(String token) {
        User user = authService.valid(token);
        if (user == null){
            return Result.failure(ResultCode.USER_NOT_EXIST);
        }
        return Result.success(user);
    }
}
