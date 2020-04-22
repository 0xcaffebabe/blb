package wang.ismy.blb.impl.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.cache.CacheApi;
import wang.ismy.blb.common.enums.ServiceName;
import wang.ismy.blb.common.result.Result;

/**
 * @author MY
 * @date 2020/4/19 10:08
 */
@FeignClient(value = ServiceName.CACHE_SERVICE,fallback = CacheApiClient.Fallback.class)
public interface CacheApiClient extends CacheApi {

    @Component
    class Fallback extends CacheApi.Fallback implements CacheApiClient{}
}
