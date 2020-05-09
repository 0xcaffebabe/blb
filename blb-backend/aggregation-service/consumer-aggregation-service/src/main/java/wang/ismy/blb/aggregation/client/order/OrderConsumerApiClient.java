package wang.ismy.blb.aggregation.client.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.order.OrderConsumerApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/5/9 14:08
 */
@FeignClient(ServiceName.ORDER_SERVICE)
public interface OrderConsumerApiClient extends OrderConsumerApi {
    @Component
    class Fallback extends OrderConsumerApi.Fallback implements OrderConsumerApiClient{}

}
