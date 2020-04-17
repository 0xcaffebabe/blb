package wang.ismy.blb.impl.product.auth;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author MY
 * @date 2020/4/13 8:43
 */
@SpringBootApplication
@EnableSwagger2Doc
@ComponentScan("wang.ismy.blb")
@EnableFeignClients
@EnableDiscoveryClient
@EnableConfigurationProperties
public class AuthApp {
    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class, args);
    }
}
