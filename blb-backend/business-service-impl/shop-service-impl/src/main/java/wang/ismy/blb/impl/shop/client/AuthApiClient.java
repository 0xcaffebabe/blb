package wang.ismy.blb.impl.shop.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.auth.AuthApi;
import wang.ismy.blb.common.enums.ServiceName;

import java.security.SecureRandom;

/**
 * @author MY
 * @date 2020/4/23 10:26
 */
@FeignClient(value = ServiceName.AUTH_SERVICE,fallback = AuthApiClient.Fallback.class)
public interface AuthApiClient extends AuthApi {
    @Component
    class Fallback extends AuthApi.Fallback implements AuthApiClient{}
}
