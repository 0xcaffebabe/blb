package wang.ismy.blb.aggregation.client.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.order.OrderSellerApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/5/25 7:31
 */
@FeignClient(value = ServiceName.ORDER_SERVICE,fallback = OrderSellerApiClient.Fallback.class)
public interface OrderSellerApiClient extends OrderSellerApi {
    @Component
    class Fallback extends OrderSellerApi.Fallback implements OrderSellerApiClient{}
}
