package wang.ismy.blb.aggregation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.product.ProductApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/5/12 19:39
 */
@FeignClient(value = ServiceName.PRODUCT_SERVICE,fallback = ProductApiClient.Fallback.class)
public interface ProductApiClient extends ProductApi {
    @Component
    class Fallback extends ProductApi.Fallback implements ProductApiClient {}
}
