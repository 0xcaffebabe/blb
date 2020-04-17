package wang.ismy.blb.impl.product.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author MY
 * @date 2020/4/13 13:07
 */
@Configuration
@ConfigurationProperties(prefix = "auth")
@Data
public class AuthConfigProperties {
    private Long tokenTtl;
}
