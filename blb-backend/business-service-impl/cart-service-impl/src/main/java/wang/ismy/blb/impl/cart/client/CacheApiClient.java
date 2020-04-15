package wang.ismy.blb.impl.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import wang.ismy.blb.api.cache.CacheApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/4/15 9:25
 */
@FeignClient(ServiceName.CACHE_SERVICE)
public interface CacheApiClient extends CacheApi { }
