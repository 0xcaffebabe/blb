package wang.ismy.blb.aggregation.client.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.consumer.ConsumerDeliveryApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/5/8 15:40
 */
@FeignClient(value = ServiceName.CONSUMER_SERVICE,fallback = ConsumerDeliveryApiClient.Fallback.class)
public interface ConsumerDeliveryApiClient extends ConsumerDeliveryApi {
    @Component
    class Fallback extends ConsumerDeliveryApi.Fallback implements ConsumerDeliveryApiClient{}
}
