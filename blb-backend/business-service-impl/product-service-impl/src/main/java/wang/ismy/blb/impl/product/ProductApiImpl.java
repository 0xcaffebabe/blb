package wang.ismy.blb.impl.product;

import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.api.product.ProductApi;
import wang.ismy.blb.api.product.pojo.dto.CartProductGetDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductSpecDTO;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.impl.product.service.ProductService;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/17 8:39
 */
@RestController
@AllArgsConstructor
public class ProductApiImpl implements ProductApi {

    private ProductService productService;

    @Override
    public Result<ProductDTO> getProduct(Long productId) {
        ProductDTO product = productService.getProduct(productId);
        if (product == null){
            return Result.failure(ResultCode.PRODUCT_NOT_EXIST);
        }
        return Result.success(product);
    }

    @Override
    public Result<List<ProductDTO>> getProductList(List<Long> productIdList) {
        if (CollectionUtils.isEmpty(productIdList)){
            return Result.failure(ResultCode.PARAM_IS_BLANK);
        }
        return Result.success(productService.getProductList(productIdList));
    }

    @Override
    public Result<List<ProductDTO>> getListByProductAndSpecList(List<CartProductGetDTO> list) {
        List<ProductDTO> productList = productService.getProductAndSpecList(list);
        if (productList == null){
            return Result.failure(ResultCode.PRODUCT_OR_SPEC_NOT_EXIST);
        }
        return Result.success(productList);
    }

    @Override
    public Result<ProductSpecDTO> getProductSpec(Long specId) {
        ProductSpecDTO spec = productService.getProductSpec(specId);
        if (spec == null){
            return Result.failure(ResultCode.PRODUCT_SPEC_NOT_EXIST);
        }
        return Result.success(spec);
    }
}
