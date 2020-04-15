package wang.ismy.blb.impl.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import wang.ismy.blb.api.auth.AuthApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/4/15 10:26
 */
@FeignClient(ServiceName.AUTH_SERVICE)
public interface AuthApiClient extends AuthApi { }
