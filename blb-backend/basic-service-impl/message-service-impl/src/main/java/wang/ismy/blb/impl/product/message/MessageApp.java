package wang.ismy.blb.impl.product.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author MY
 * @date 2020/4/14 8:38
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MessageApp {
    public static void main(String[] args) {
        SpringApplication.run(MessageApp.class,args);
    }
}
