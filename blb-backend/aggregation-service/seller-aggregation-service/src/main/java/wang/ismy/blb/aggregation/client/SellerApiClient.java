package wang.ismy.blb.aggregation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.seller.SellerApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/5/18 18:47
 */
@FeignClient(value = ServiceName.SELLER_SERVICE,fallback = SellerApiClient.Fallback.class)
public interface SellerApiClient extends SellerApi {
    @Component
    class Fallback extends SellerApi.Fallback implements SellerApiClient{}
}
