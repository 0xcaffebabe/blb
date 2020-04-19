package wang.ismy.blb.impl.product;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;
import wang.ismy.blb.api.cache.CacheApi;
import wang.ismy.blb.api.cache.CacheService;
import wang.ismy.blb.api.cache.JsonCacheSerializer;
import wang.ismy.blb.common.SnowFlake;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/4/17 8:38
 */
@SpringBootApplication
@EnableSwagger2Doc
@ComponentScan("wang.ismy.blb")
@EnableDiscoveryClient
@EnableJpaRepositories
@EntityScan("wang.ismy.blb")
@EnableFeignClients
public class ProductApp {
    public static void main(String[] args) {
        SpringApplication.run(ProductApp.class, args);
    }

    @Bean
    public SnowFlake snowFlake(){
        return new SnowFlake(ServiceName.DATA_CENTER_ID, ServiceName.PRODUCT_SERVICE_ID);
    }

    @Bean
    public CacheService cacheService(CacheApi cacheApi){
        return new CacheService(cacheApi,ServiceName.PRODUCT_SERVICE,new JsonCacheSerializer()){
            @Override
            protected long getLocalCacheTtl() {
                return ProductConstant.CACHE_TTL;
            }
        };
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
