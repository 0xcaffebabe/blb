package wang.ismy.blb.impl.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.product.ProductApi;
import wang.ismy.blb.api.product.pojo.dto.CartProductGetDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductSpecDTO;
import wang.ismy.blb.common.enums.ServiceName;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/15 10:20
 */
@FeignClient(value = ServiceName.PRODUCT_SERVICE,fallback = ProductApiClient.Fallback.class)
public interface ProductApiClient extends ProductApi {
    @Component
    class Fallback extends ProductApi.Fallback implements ProductApiClient{ }
}
