package wang.ismy.blb.impl.product.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.api.product.ProductSellerApi;
import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductCreateDTO;
import wang.ismy.blb.api.product.pojo.dto.ShopProductDTO;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.impl.product.service.ProductSellerService;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/20 8:18
 */
@RestController
@AllArgsConstructor
public class ProductSellerApiImpl implements ProductSellerApi {

    private final ProductSellerService sellerService;

    @Override
    public Result<Void> addProduct(String token, ProductCreateDTO productCreateDTO) {
        sellerService.addProduct(token,productCreateDTO);
        return Result.success();
    }

    @Override
    public Result<Void> updateProduct(String token, Long productId, ProductCreateDTO productCreateDTO) {
        sellerService.updateProduct(token,productId,productCreateDTO);
        return Result.success();
    }

    @Override
    public Result<Void> deleteProduct(String token, Long productId) {
        sellerService.deleteProduct(token,productId);
        return Result.success();
    }

    @Override
    public Result<Page<ShopProductDTO>> getProductList(String token, Pageable pageable) {
        return Result.success(sellerService.getProductList(token,pageable));
    }

    @Override
    public Result<List<ProductCategoryDTO>> getCategoryList(String token) {
        return Result.success(sellerService.getCategoryList(token));
    }
}
