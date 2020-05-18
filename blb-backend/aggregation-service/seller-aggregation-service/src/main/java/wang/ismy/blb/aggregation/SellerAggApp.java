package wang.ismy.blb.aggregation;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author MY
 * @date 2020/5/18 9:03
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("wang.ismy.blb")
@EnableSwagger2Doc
public class SellerAggApp {
    public static void main(String[] args) {
        SpringApplication.run(SellerAggApp.class,args);
    }
}
