package wang.ismy.blb.aggregation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.pay.PayApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/5/9 13:43
 */
@FeignClient(ServiceName.PAY_SERVICE)
public interface PayApiClient extends PayApi {
    @Component
    class Fallback extends PayApi.Fallback implements PayApiClient{}
}
