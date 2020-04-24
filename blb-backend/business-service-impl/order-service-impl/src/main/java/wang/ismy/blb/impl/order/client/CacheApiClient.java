package wang.ismy.blb.impl.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.cache.CacheApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/4/24 13:15
 */
@FeignClient(value = ServiceName.AUTH_SERVICE,fallback = CacheApiClient.Fallback.class)
public interface CacheApiClient extends CacheApi {
    @Component
    class Fallback extends CacheApi.Fallback implements CacheApiClient{}
}
