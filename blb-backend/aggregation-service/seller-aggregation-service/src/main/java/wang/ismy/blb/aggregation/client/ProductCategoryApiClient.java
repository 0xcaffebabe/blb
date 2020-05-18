package wang.ismy.blb.aggregation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.product.ProductCategoryApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/5/7 19:49
 */
@FeignClient(ServiceName.PRODUCT_SERVICE)
public interface ProductCategoryApiClient extends ProductCategoryApi {
    @Component
    class Fallback extends ProductCategoryApi.Fallback implements ProductCategoryApiClient{}
}
