package wang.ismy.blb.impl.cache;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author MY
 * @date 2020/4/11 15:23
 */
@SpringBootApplication
@EnableSwagger2Doc
@EnableDiscoveryClient
public class CacheApp {
    public static void main(String[] args) {
        SpringApplication.run(CacheApp.class, args);
    }
}
