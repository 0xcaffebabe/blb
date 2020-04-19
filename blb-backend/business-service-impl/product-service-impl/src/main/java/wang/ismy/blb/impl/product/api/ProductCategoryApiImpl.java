package wang.ismy.blb.impl.product.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.api.product.ProductCategoryApi;
import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ShopProductDTO;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.impl.product.service.ProductCategoryService;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/18 8:28
 */
@RestController
@AllArgsConstructor
public class ProductCategoryApiImpl implements ProductCategoryApi {

    private final ProductCategoryService productCategoryService;

    @Override
    public Result<List<ShopProductDTO>> getProductByCategory(Long categoryId) {
        return Result.success(productCategoryService.getProductByCategory(categoryId));
    }

    @Override
    public Result<List<ProductCategoryDTO>> getCategoryList(Long shopId) {
        return Result.success(productCategoryService.getCategoryList(shopId));
    }

    @Override
    public Result<Void> addCategory(String token,ProductCategoryDTO productCategoryDTO) {
        productCategoryService.addCategory(token,productCategoryDTO);
        return Result.success();
    }

    @Override
    public Result<Void> updateCategory(String token,ProductCategoryDTO productCategoryDTO) {
        productCategoryService.update(token,productCategoryDTO);
        return Result.success();
    }

    @Override
    public Result<Void> deleteCategory(String token,Long categoryId) {
        productCategoryService.delete(token,categoryId);
        return Result.success();
    }
}
