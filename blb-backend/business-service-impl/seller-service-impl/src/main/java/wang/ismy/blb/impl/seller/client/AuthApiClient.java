package wang.ismy.blb.impl.seller.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.auth.AuthApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/5/3 9:30
 */
@FeignClient(value = ServiceName.AUTH_SERVICE,fallback = AuthApiClient.Fallback.class)
public interface AuthApiClient extends AuthApi {
    @Component
    class Fallback extends AuthApi.Fallback implements AuthApiClient{}
}
