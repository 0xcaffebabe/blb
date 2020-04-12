package wang.ismy.blb.impl.cache;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author MY
 * @date 2020/4/11 15:23
 */
@SpringBootApplication
@EnableSwagger2Doc
public class CacheApp {
    public static void main(String[] args) {
        SpringApplication.run(CacheApp.class, args);
    }
}
