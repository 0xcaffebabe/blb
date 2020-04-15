package wang.ismy.blb.impl.cart;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author MY
 * @date 2020/4/15 8:36
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2Doc
@ComponentScan("wang.ismy.blb")
@EnableFeignClients
public class CartApp {
    public static void main(String[] args) {
        SpringApplication.run(CartApp.class,args);
    }
}
