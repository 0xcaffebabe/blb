package wang.ismy.blb.govern.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * @author MY
 * @date 2020/5/6 8:46
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayService {
    public static void main(String[] args) {
        SpringApplication.run(GatewayService.class,args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("consumer-router", r -> r.header("TYPE","CONSUMER")
                        .uri("lb://consumer-aggregation-service"))
                .build();
    }
}
