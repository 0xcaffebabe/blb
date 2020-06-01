package wang.ismy.blb.aggregation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.rider.RiderApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/6/1 16:12
 */
@FeignClient(value = ServiceName.RIDER_SERVICE,fallback = RiderApiClient.Fallback.class)
public interface RiderApiClient extends RiderApi {
    @Component
    class Fallback extends RiderApi.Fallback implements RiderApiClient{}
}
