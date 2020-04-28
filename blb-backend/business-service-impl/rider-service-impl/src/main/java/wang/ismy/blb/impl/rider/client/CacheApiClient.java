package wang.ismy.blb.impl.rider.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.cache.CacheApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/4/28 10:43
 */
@FeignClient(value = ServiceName.CACHE_SERVICE,fallback = CacheApiClient.Fallback.class)
@Primary
public interface CacheApiClient extends CacheApi {
    @Component
    class Fallback extends CacheApi.Fallback implements CacheApiClient{}
}
