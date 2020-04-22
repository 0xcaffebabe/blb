package wang.ismy.blb.impl.consumer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.auth.AuthApi;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.enums.ServiceName;
import wang.ismy.blb.common.result.Result;

import javax.validation.Valid;

/**
 * @author MY
 * @date 2020/4/21 10:33
 */
@FeignClient(value = ServiceName.AUTH_SERVICE,fallback = AuthApiClient.Fallback.class)
public interface AuthApiClient extends AuthApi {
    @Component
    class Fallback implements  AuthApiClient{

        @Override
        public Result<String> auth(@Valid User user) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }

        @Override
        public Result<User> valid(String token) {
            return null;
        }
    }
}
