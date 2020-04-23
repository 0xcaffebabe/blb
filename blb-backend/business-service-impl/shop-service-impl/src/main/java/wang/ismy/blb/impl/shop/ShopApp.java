package wang.ismy.blb.impl.shop;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import wang.ismy.blb.common.SnowFlake;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/4/23 8:39
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("wang.ismy.blb")
@EntityScan("wang.ismy.blb")
@EnableSwagger2Doc
@EnableFeignClients
public class ShopApp {
    public static void main(String[] args) {
        SpringApplication.run(ShopApp.class,args);
    }

    @Bean
    public SnowFlake snowFlake(){
        return new SnowFlake(ServiceName.DATA_CENTER_ID,ServiceName.SHOP_SERVICE_ID);
    }
}
