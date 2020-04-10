package wang.ismy.blb.api.product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.api.product.pojo.dto.CartProductGetDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductCreateDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductDTO;
import wang.ismy.blb.api.product.pojo.dto.ShopProductDTO;
import wang.ismy.blb.common.Pageable;
import wang.ismy.blb.common.Result;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/10 8:40
 */
@Api(tags = "商品服务接口")
public interface ProductApi {

    /**
     * 根据商品ID拉取详细信息
     *
     * @param productId
     * @return
     */
    @ApiOperation("根据商品ID拉取详细信息")
    @ApiImplicitParam(paramType = "path", name = "productId", dataType = "Long", required = true, value = "商品ID")
    @GetMapping("{productId}")
    Result<ProductDTO> getProduct(@PathVariable("productId") String productId);

    /**
     * 根据商品ID批量拉取商品详细信息
     *
     * @param productIdList 商品ID列表
     * @return
     */
    @ApiOperation("根据商品ID批量拉取商品详细信息")
    @ApiImplicitParam(paramType = "query", name = "productIdList", dataType = "List", required = true, value = "商品ID列表")
    @GetMapping("list")
    Result<List<ProductDTO>> getProductList(@RequestParam("productIdList") List<Long> productIdList);

    /**
     * 根据商品ID与规格ID列表拉取商品信息
     *
     * @param list （商品ID与规格ID）列表
     * @return
     */
    @ApiOperation("根据商品ID与规格ID列表拉取商品信息")
    @GetMapping("spec/list")
    Result<List<ProductDTO>> getListByProductAndSpecList(@RequestBody List<CartProductGetDTO> list);


}