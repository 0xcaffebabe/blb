package wang.ismy.blb.impl.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author MY
 * @date 2020/4/13 11:47
 */
@Configuration
@Slf4j
public class MyWebMvcRegistrations implements WebMvcRegistrations {

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping(){
            @Override
            protected boolean isHandler(Class<?> beanType) {
                return super.isHandler(beanType) && !AnnotatedElementUtils.hasAnnotation(beanType, FeignClient.class);
            }
        };
    }
}
