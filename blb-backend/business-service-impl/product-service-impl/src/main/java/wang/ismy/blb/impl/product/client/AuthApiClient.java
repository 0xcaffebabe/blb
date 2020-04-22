package wang.ismy.blb.impl.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.auth.AuthApi;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.auth.UserTypeEnum;
import wang.ismy.blb.common.enums.ServiceName;
import wang.ismy.blb.common.result.Result;

import javax.validation.Valid;

/**
 * @author MY
 * @date 2020/4/18 9:58
 */
@FeignClient(value = ServiceName.AUTH_SERVICE,fallback = AuthApiClient.Fallback.class)
public interface AuthApiClient extends AuthApi {

    @Component
    class Fallback extends AuthApi.Fallback implements AuthApiClient{ }
}
