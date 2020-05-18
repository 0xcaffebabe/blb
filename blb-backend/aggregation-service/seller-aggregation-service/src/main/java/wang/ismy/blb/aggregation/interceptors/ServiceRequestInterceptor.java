package wang.ismy.blb.aggregation.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.util.CurrentRequestUtils;

/**
 * 拦截feign客户端的请求添加header
 * @author MY
 * @date 2020/5/8 15:03
 */
@Component
public class ServiceRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        // 进行请求转发时，需要传递客户端传递过来的token
        template.header(SystemConstant.TOKEN,CurrentRequestUtils.getHeader(SystemConstant.TOKEN));
    }
}
