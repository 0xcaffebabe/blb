package wang.ismy.blb.aggregation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.product.ProductSellerApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/5/25 7:58
 */
@FeignClient(value = ServiceName.PRODUCT_SERVICE,fallback = ProductSellerApiClient.Fallback.class)
public interface ProductSellerApiClient extends ProductSellerApi {
    @Component
    class Fallback extends ProductSellerApi.Fallback implements ProductSellerApiClient{}
}
