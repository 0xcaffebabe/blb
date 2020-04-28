package wang.ismy.blb.impl.rider.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.order.OrderApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/4/28 10:06
 */
@FeignClient(value = ServiceName.ORDER_SERVICE,fallback = OrderApiClient.Fallback.class)
public interface OrderApiClient extends OrderApi {
    @Component
    class Fallback extends OrderApi.Fallback implements OrderApiClient{}
}
