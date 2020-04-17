package wang.ismy.blb.impl.product;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
public class ProductApp {
    public static void main(String[] args) {
        SpringApplication.run(ProductApp.class, args);
    }
}
