package wang.ismy.blb.aggregation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.cart.CartApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/5/8 14:57
 */
@FeignClient(ServiceName.CART_SERVICE)
public interface CartApiClient extends CartApi {
    @Component
    class Fallback extends CartApi.Fallback implements CartApiClient{}
}
