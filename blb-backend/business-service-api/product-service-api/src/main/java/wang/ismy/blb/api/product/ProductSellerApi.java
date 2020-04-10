package wang.ismy.blb.api.product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductCreateDTO;
import wang.ismy.blb.api.product.pojo.dto.ShopProductDTO;
import wang.ismy.blb.common.Pageable;
import wang.ismy.blb.common.Result;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/10 9:28
 */
@Api(tags = "商家商品服务接口")
@RequestMapping("seller")
public interface ProductSellerApi {
    /**
     * 添加商品
     *
     * @param productCreateDTO
     * @return
     */
    @ApiOperation("添加商品")
    @PostMapping("")
    Result<Void> addProduct(@RequestBody ProductCreateDTO productCreateDTO);

    /**
     * 更新商品
     * @param productId
     * @param productCreateDTO
     * @return
     */
    @ApiOperation("更新商品")
    @ApiImplicitParam(paramType = "path", name = "productId", dataType = "Long", required = true, value = "商品ID")
    @PutMapping("{productId}")
    Result<Void> updateProduct(@PathVariable("productId") Long productId,
                               @RequestBody ProductCreateDTO productCreateDTO);

    /**
     * 删除商品
     * @param productId
     * @return
     */
    @ApiOperation("删除商品")
    @ApiImplicitParam(paramType = "path", name = "productId", dataType = "Long", required = true, value = "商品ID")
    @DeleteMapping("{productId}")
    Result<Void> deleteProduct(@PathVariable("productId") String productId);

    /**
     * 商家获取自己的商品列表
     * @param pageable
     * @return
     */
    @ApiOperation("商家获取自己的商品列表")
    @GetMapping("list")
    Result<List<ShopProductDTO>> getProductList(Pageable pageable);

    /**
     * 商家获取自己的商品目录列表
     * @return
     */
    @ApiOperation("商家获取自己的商品目录列表")
    @GetMapping("category/list")
    Result<List<ProductCategoryDTO>> getCategoryList();


}
