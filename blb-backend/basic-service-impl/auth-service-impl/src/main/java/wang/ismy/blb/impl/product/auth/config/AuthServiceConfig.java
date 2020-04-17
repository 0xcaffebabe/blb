package wang.ismy.blb.impl.product.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wang.ismy.blb.api.cache.CacheApi;
import wang.ismy.blb.api.cache.CacheService;
import wang.ismy.blb.api.cache.JsonCacheSerializer;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/4/13 11:50
 */
@Configuration
public class AuthServiceConfig {
    @Bean
    public CacheService cacheService(CacheApi cacheApi){
        return new CacheService(cacheApi, ServiceName.AUTH_SERVICE,new JsonCacheSerializer());
    }
}
