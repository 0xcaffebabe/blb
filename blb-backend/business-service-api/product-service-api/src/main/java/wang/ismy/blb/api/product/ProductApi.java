package wang.ismy.blb.api.product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.api.product.pojo.dto.CartProductGetDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductSpecDTO;
import wang.ismy.blb.common.result.Result;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/10 8:40
 */
@Api(tags = "商品服务接口")
@RequestMapping(value = "v1/api",produces = MediaType.APPLICATION_JSON_VALUE)
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
    Result<ProductDTO> getProduct(@PathVariable("productId") Long productId);

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

    /**
     * 根据规格ID查询规格信息
     * @param specId
     * @return
     */
    @ApiOperation("根据规格ID查询规格信息")
    @ApiImplicitParam(paramType = "path", name = "specId", dataType = "Long", required = true, value = "规格ID")
    @GetMapping("spec/{specId}")
    Result<ProductSpecDTO> getProductSpec(@PathVariable("specId") Long specId);
}
