package wang.ismy.blb.impl.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.auth.AuthApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/4/24 10:27
 */
@FeignClient(value = ServiceName.AUTH_SERVICE,fallback = AuthApiClient.Fallback.class)
public interface AuthApiClient extends AuthApi {
    @Component
    class Fallback extends AuthApi.Fallback implements AuthApiClient{}
}
