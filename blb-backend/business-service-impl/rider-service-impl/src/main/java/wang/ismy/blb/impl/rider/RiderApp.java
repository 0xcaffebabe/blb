package wang.ismy.blb.impl.rider;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import wang.ismy.blb.api.cache.CacheApi;
import wang.ismy.blb.api.cache.CacheService;
import wang.ismy.blb.api.cache.JsonCacheSerializer;
import wang.ismy.blb.common.SnowFlake;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/4/28 8:39
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2Doc
@ComponentScan("wang.ismy.blb")
@EntityScan("wang.ismy.blb")
@EnableFeignClients
public class RiderApp {
    public static void main(String[] args) {
        SpringApplication.run(RiderApp.class,args);
    }

    @Bean
    public SnowFlake snowFlake(){
        return new SnowFlake(ServiceName.DATA_CENTER_ID,ServiceName.RIDER_SERVICE_ID);
    }

    @Bean
    public CacheService cacheService(CacheApi cacheApi){
        return new CacheService(cacheApi,ServiceName.RIDER_SERVICE,new JsonCacheSerializer());
    }
}
