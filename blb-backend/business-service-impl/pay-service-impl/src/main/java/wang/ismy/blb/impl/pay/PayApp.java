package wang.ismy.blb.impl.pay;

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
 * @date 2020/4/26 8:44
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2Doc
@ComponentScan("wang.ismy.blb")
@EntityScan("wang.ismy.blb")
@EnableFeignClients
public class PayApp {
    public static void main(String[] args) {
        SpringApplication.run(PayApp.class,args);
    }

    @Bean
    public SnowFlake snowFlake(){
        return new SnowFlake(ServiceName.DATA_CENTER_ID,ServiceName.PAY_SERVICE_ID);
    }
}
