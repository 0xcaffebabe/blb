package wang.ismy.blb.impl.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.product.ProductApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/4/24 10:31
 */
@FeignClient(value = ServiceName.PRODUCT_SERVICE)
public interface ProductApiClient extends ProductApi {
    @Component
    class Fallback extends ProductApi.Fallback implements ProductApiClient{}
}
