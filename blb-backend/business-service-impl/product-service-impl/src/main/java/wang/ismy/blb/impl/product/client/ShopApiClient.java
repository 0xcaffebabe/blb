package wang.ismy.blb.impl.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.shop.ShopApi;
import wang.ismy.blb.api.shop.pojo.dto.ShopCreateDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoUpdateDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopItemDTO;
import wang.ismy.blb.common.enums.ServiceName;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;

import java.util.List;
import java.util.Map;

/**
 * @author MY
 * @date 2020/4/18 10:17
 */
@FeignClient(value = ServiceName.SHOP_SERVICE,fallback = ShopApiClient.Fallback.class)
public interface ShopApiClient extends ShopApi {
    @Component
    class Fallback extends ShopApi.Fallback implements ShopApiClient { }
}
