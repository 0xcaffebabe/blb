package wang.ismy.blb.aggregation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.shop.ShopCategoryApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/5/7 8:48
 */
@FeignClient(value = ServiceName.SHOP_SERVICE,fallback = ShopCategoryApiClient.Fallback.class)
public interface ShopCategoryApiClient extends ShopCategoryApi {
    @Component
    class Fallback extends ShopCategoryApi.Fallback implements ShopCategoryApiClient{}
}
