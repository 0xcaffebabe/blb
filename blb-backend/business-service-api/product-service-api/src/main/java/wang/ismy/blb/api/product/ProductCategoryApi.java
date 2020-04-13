package wang.ismy.blb.api.product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ShopProductDTO;
import wang.ismy.blb.common.result.Result;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/10 9:10
 */
@Api(tags = "商品分类服务接口")
@RequestMapping("category")
public interface ProductCategoryApi {

    /**
     * 根据分类ID获取商品列表
     * @param categoryId
     * @return
     */
    @ApiOperation("根据分类ID获取商品列表")
    @ApiImplicitParam(paramType = "path", name = "categoryId", dataType = "Long", required = true, value = "商品分类ID")
    @GetMapping("product/{categoryId}")
    Result<List<ShopProductDTO>> getProductByCategory(@PathVariable("categoryId") Long categoryId);

    /**
     * 根据店铺ID获取目录列表
     * @param shopId
     * @return
     */
    @ApiOperation("根据店铺ID获取目录列表")
    @ApiImplicitParam(paramType = "path", name = "categoryId", dataType = "Long", required = true, value = "商品分类ID")
    @GetMapping("shop/{shopId}")
    Result<List<ProductCategoryDTO>> getCategoryList(@PathVariable("shopId") String shopId);

    /**
     * 商家添加目录
     * @param productCategoryDTO
     * @return
     */
    @ApiOperation("商家添加目录")
    @PostMapping("")
    Result<Void> addCategory(@RequestBody ProductCategoryDTO productCategoryDTO);

    /**
     * 商家更新目录
     * @param productCategoryDTO
     * @return
     */
    @ApiOperation("商家更新目录")
    @PostMapping("")
    Result<Void> updateCategory(@RequestBody ProductCategoryDTO productCategoryDTO);

    /**
     * 商家删除目录
     * @param categoryId
     * @return
     */
    @ApiOperation("商家删除目录")
    @ApiImplicitParam(paramType = "path", name = "categoryId", dataType = "Long", required = true, value = "商品分类ID")
    @DeleteMapping("{categoryId}")
    Result<Void> deleteCategory(@PathVariable("categoryId") Long categoryId);
}
