package wang.ismy.blb.api.cart;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.result.Result;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/10 10:52
 */
@Api(tags = "购物车服务接口")
@RequestMapping(value = "v1/api",produces = MediaType.APPLICATION_JSON_VALUE)
public interface CartApi {

    /**
     * 加入购物车
     * @param token 消费者token
     * @param productId
     * @param specId
     * @param quantity
     * @return
     */
    @ApiOperation("加入购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "TOKEN", dataType = "String", required = true, value = "消费者token"),
            @ApiImplicitParam(paramType = "path", name = "productId", dataType = "Long", required = true, value = "商品ID"),
            @ApiImplicitParam(paramType = "path", name = "specId", dataType = "Long", required = true, value = "规格ID"),
            @ApiImplicitParam(paramType = "query", name = "quantity", dataType = "Long", required = true, value = "商品数量")
    })
    @PostMapping("product/{productId}/{specId}")
    Result<Void> addProduct(
                            @RequestHeader(SystemConstant.TOKEN) String token,
                            @PathVariable("productId") Long productId,
                            @PathVariable("specId") Long specId,
                            @RequestParam("quantity") Long quantity);

    /**
     * 获取消费者的购物车列表
     * @param token 消费者token
     * @param shopId
     * @return
     */
    @ApiOperation("获取消费者某间店铺的购物车列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "TOKEN", dataType = "String", required = true, value = "消费者token"),
            @ApiImplicitParam(paramType = "path", name = "shopId", dataType = "Long", required = true, value = "店铺ID")
    })
    @GetMapping("list/{shopId}")
    Result<List<CartItem>> getCartList(@RequestHeader(SystemConstant.TOKEN) String token , @PathVariable("shopId") Long shopId);

    /**
     * 清空购物车
     * @param shopId
     * @return
     */
    @ApiOperation("清空购物车")
    @DeleteMapping("list/{shopId}")
    Result<Void> delAllProductList(@PathVariable("shopId") Long shopId);

    /**
     * 删除购物车的某个商品
     * @param token
     * @param productId
     * @param specId
     * @return
     */
    @ApiOperation("删除购物车的某个商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "TOKEN", dataType = "String", required = true, value = "消费者token"),
            @ApiImplicitParam(paramType = "path", name = "productId", dataType = "Long", required = true, value = "商品ID"),
            @ApiImplicitParam(paramType = "path", name = "specId", dataType = "Long", required = true, value = "规格ID"),
    })
    @DeleteMapping("product/{productId}/{specId}")
    Result<Void> deleteProduct(@RequestHeader(SystemConstant.TOKEN) String token,
                               @PathVariable("productId") Long productId,
                               @PathVariable("specId") Long specId);

    class Fallback implements CartApi{

        @Override
        public Result<Void> addProduct(String token, Long productId, Long specId, Long quantity) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 购物车服务 增加商品接口失败");
        }

        @Override
        public Result<List<CartItem>> getCartList(String token, Long shopId) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 购物车服务 获取商品列表接口失败");
        }

        @Override
        public Result<Void> delAllProductList(Long shopId) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 购物车服务 清空购物车接口失败");
        }

        @Override
        public Result<Void> deleteProduct(String token, Long productId, Long specId) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 购物车服务 删除商品接口失败");
        }
    }
}
