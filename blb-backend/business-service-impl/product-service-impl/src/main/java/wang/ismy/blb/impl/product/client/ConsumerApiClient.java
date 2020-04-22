package wang.ismy.blb.impl.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.consumer.ConsumerApi;
import wang.ismy.blb.api.consumer.pojo.dto.*;
import wang.ismy.blb.common.enums.ServiceName;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/4/19 13:17
 */
@FeignClient(value = ServiceName.CONSUMER_SERVICE,fallback = ConsumerApiClient.Fallback.class)
public interface ConsumerApiClient extends ConsumerApi {

    @Component
    class Fallback extends ConsumerApi.Fallback implements ConsumerApiClient{}

}
