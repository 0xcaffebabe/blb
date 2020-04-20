package wang.ismy.blb.api.product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ShopProductDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Result;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/10 9:10
 */
@Api(tags = "商品分类服务接口")
@RequestMapping(value = "v1/api/category",produces = MediaType.APPLICATION_JSON_VALUE)
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
    @ApiImplicitParam(paramType = "path", name = "shopId", dataType = "Long", required = true, value = "店铺ID")
    @GetMapping("shop/{shopId}")
    Result<List<ProductCategoryDTO>> getCategoryList(@PathVariable("shopId") Long shopId);

    /**
     * 商家添加目录
     * @param token 商家token
     * @param productCategoryDTO
     * @return
     */
    @ApiOperation("商家添加目录")
    @ApiImplicitParam(paramType = "header", name = "TOKEN", dataType = "String", required = true, value = "商家token")
    @PutMapping("")
    Result<Void> addCategory(@RequestHeader(SystemConstant.TOKEN) String token,
                             @RequestBody ProductCategoryDTO productCategoryDTO);

    /**
     * 商家更新目录
     * @param token 商家token
     * @param productCategoryDTO
     * @return
     */
    @ApiOperation("商家更新目录")
    @ApiImplicitParam(paramType = "header", name = "TOKEN", dataType = "String", required = true, value = "商家token")
    @PostMapping("")
    Result<Void> updateCategory(@RequestHeader(SystemConstant.TOKEN) String token,
                                @RequestBody ProductCategoryDTO productCategoryDTO);

    /**
     * 商家删除目录
     * @param token 商家token
     * @param categoryId
     * @return
     */
    @ApiOperation("商家删除目录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "TOKEN", dataType = "String", required = true, value = "商家token"),
            @ApiImplicitParam(paramType = "path", name = "categoryId", dataType = "Long", required = true, value = "商品分类ID")
    })
    @DeleteMapping("{categoryId}")
    Result<Void> deleteCategory(@RequestHeader(SystemConstant.TOKEN) String token,
                                @PathVariable("categoryId") Long categoryId);
}
