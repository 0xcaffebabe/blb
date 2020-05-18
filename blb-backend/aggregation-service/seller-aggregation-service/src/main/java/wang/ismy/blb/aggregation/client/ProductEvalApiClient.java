package wang.ismy.blb.aggregation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.product.ProductEvaluationApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/5/8 14:44
 */
@FeignClient(value = ServiceName.PRODUCT_SERVICE,fallback = ProductEvalApiClient.Fallback.class)
public interface ProductEvalApiClient extends ProductEvaluationApi {
    @Component
    class Fallback extends ProductEvaluationApi.Fallback implements ProductEvalApiClient{}
}
