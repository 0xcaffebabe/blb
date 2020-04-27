package wang.ismy.blb.impl.pay.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.order.OrderApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/4/27 9:05
 */
@FeignClient(value = ServiceName.ORDER_SERVICE,fallback = OrderApiClient.Fallback.class)
public interface OrderApiClient extends OrderApi {
    @Component
    class Fallback extends OrderApi.Fallback implements OrderApiClient{}
}
