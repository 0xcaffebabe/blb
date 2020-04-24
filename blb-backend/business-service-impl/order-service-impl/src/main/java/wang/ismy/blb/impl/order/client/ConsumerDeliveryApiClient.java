package wang.ismy.blb.impl.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.consumer.ConsumerDeliveryApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/4/24 10:56
 */
@FeignClient(value = ServiceName.CONSUMER_SERVICE)
public interface ConsumerDeliveryApiClient extends ConsumerDeliveryApi {
    @Component
    class Fallback extends ConsumerDeliveryApi.Fallback implements ConsumerDeliveryApiClient{}
}
