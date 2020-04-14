package wang.ismy.blb.impl.upload;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author MY
 * @date 2020/4/14 11:04
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2Doc
@ComponentScan("wang.ismy.blb")
public class UploadApp {
    public static void main(String[] args) {
        SpringApplication.run(UploadApp.class,args);
    }
}
