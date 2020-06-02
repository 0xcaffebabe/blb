package wang.ismy.blb.aggregation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.rider.RiderOrderApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/6/2 8:28
 */
@FeignClient(value = ServiceName.RIDER_SERVICE,fallback = RiderOrderApiClient.Fallback.class)
public interface RiderOrderApiClient extends RiderOrderApi {
    @Component
    class Fallback extends RiderOrderApi.Fallback implements RiderOrderApiClient{}
}
