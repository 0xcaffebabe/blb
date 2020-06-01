package wang.ismy.blb.aggregation;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author MY
 * @date 2020/6/1 8:51
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2Doc
@EnableFeignClients
@ComponentScan("wang.ismy.blb")
public class RiderAggApp {
    public static void main(String[] args) {
        SpringApplication.run(RiderAggApp.class,args);
    }
}
