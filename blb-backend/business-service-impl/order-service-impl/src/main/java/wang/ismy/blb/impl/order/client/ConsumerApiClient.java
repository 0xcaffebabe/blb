package wang.ismy.blb.impl.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.consumer.ConsumerApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/4/24 10:48
 */
@FeignClient(value = ServiceName.CONSUMER_SERVICE,fallback = ConsumerApiClient.Fallback.class)
public interface ConsumerApiClient extends ConsumerApi {
    @Component
    class Fallback extends ConsumerApi.Fallback implements ConsumerApiClient{}
}
