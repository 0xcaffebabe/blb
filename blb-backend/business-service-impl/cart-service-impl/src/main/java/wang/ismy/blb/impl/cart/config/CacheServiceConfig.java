package wang.ismy.blb.impl.cart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wang.ismy.blb.api.cache.CacheApi;
import wang.ismy.blb.api.cache.CacheService;
import wang.ismy.blb.api.cache.JsonCacheSerializer;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/4/15 9:23
 */
@Configuration
public class CacheServiceConfig {

    @Bean
    public CacheService cacheService(CacheApi cacheApi){
        return new CacheService(cacheApi, ServiceName.CART_SERVICE,new JsonCacheSerializer());
    }
}
