package wang.ismy.blb.impl.rider.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.shop.ShopApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/4/29 9:10
 */
@FeignClient(value = ServiceName.SHOP_SERVICE,fallback = ShopApiClient.Fallback.class)
public interface ShopApiClient extends ShopApi {
    @Component
    class Fallback extends ShopApi.Fallback implements ShopApiClient{}
}
