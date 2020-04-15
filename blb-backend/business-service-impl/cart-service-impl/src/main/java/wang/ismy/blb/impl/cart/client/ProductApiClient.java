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

    // TODO 等待商品服务开发完成
    /** mock 实现*/
    @Component
    class Fallback implements ProductApiClient{

        @Override
        public Result<ProductDTO> getProduct(Long productId) {
            ProductDTO productDTO = MockUtils.create(ProductDTO.class);
            productDTO.setProductId(productId);
            ProductCategoryDTO category = MockUtils.create(ProductCategoryDTO.class);
            category.setShopId(1L);
            productDTO.setProductCategory(category);
            return Result.success(productDTO);
        }

        @Override
        public Result<List<ProductDTO>> getProductList(List<Long> productIdList) {
            return null;
        }

        @Override
        public Result<List<ProductDTO>> getListByProductAndSpecList(List<CartProductGetDTO> list) {
            return null;
        }

        @Override
        public Result<ProductSpecDTO> getProductSpec(Long specId) {
            ProductSpecDTO productSpecDTO = MockUtils.create(ProductSpecDTO.class);
            return Result.success(productSpecDTO);
        }
    }
}
