package wang.ismy.blb.api.product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductCreateDTO;
import wang.ismy.blb.api.product.pojo.dto.ShopProductDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/10 9:28
 */
@Api(tags = "商家商品服务接口")
@RequestMapping(value = "v1/api/seller", produces = MediaType.APPLICATION_JSON_VALUE)
public interface ProductSellerApi {
    /**
     * 添加商品
     *
     * @param token            商家token
     * @param productCreateDTO
     * @return
     */
    @ApiOperation("添加商品")
    @PostMapping("")
    Result<Void> addProduct(@RequestHeader(SystemConstant.TOKEN) String token,
                            @RequestBody ProductCreateDTO productCreateDTO);

    /**
     * 更新商品
     *
     * @param token            商家token
     * @param productId
     * @param productCreateDTO
     * @return
     */
    @ApiOperation("更新商品")
    @ApiImplicitParam(paramType = "path", name = "productId", dataType = "Long", required = true, value = "商品ID")
    @PutMapping("{productId}")
    Result<Void> updateProduct(@RequestHeader(SystemConstant.TOKEN) String token,
                               @PathVariable("productId") Long productId,
                               @RequestBody ProductCreateDTO productCreateDTO);

    /**
     * 删除商品
     *
     * @param token     商家token
     * @param productId
     * @return
     */
    @ApiOperation("删除商品")
    @ApiImplicitParam(paramType = "path", name = "productId", dataType = "Long", required = true, value = "商品ID")
    @DeleteMapping("{productId}")
    Result<Void> deleteProduct(@RequestHeader(SystemConstant.TOKEN) String token,
                               @PathVariable("productId") Long productId);

    /**
     * 商家获取自己的商品列表
     *
     * @param token    商家token
     * @param pageable
     * @return
     */
    @ApiOperation("商家获取自己的商品列表")
    @GetMapping("list")
    Result<Page<ShopProductDTO>> getProductList(@RequestHeader(SystemConstant.TOKEN) String token,
                                                Pageable pageable);

    /**
     * 商家获取自己的商品目录列表
     *
     * @param token 商家token
     * @return
     */
    @ApiOperation("商家获取自己的商品目录列表")
    @GetMapping("category/list")
    Result<List<ProductCategoryDTO>> getCategoryList(@RequestHeader(SystemConstant.TOKEN) String token);


}
