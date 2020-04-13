package wang.ismy.blb.api.cart;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import wang.ismy.blb.common.result.Result;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/10 10:52
 */
@Api(tags = "购物车服务接口")
public interface CartApi {

    /**
     * 加入购物车
     * @param productId
     * @param specId
     * @return
     */
    @ApiOperation("加入购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "productId", dataType = "Long", required = true, value = "商品ID"),
            @ApiImplicitParam(paramType = "path", name = "specId", dataType = "Long", required = true, value = "规格ID")
    })
    @PostMapping("product/{productId}/{specId}")
    Result<Void> addProduct(@PathVariable("productId") Long productId,
                            @PathVariable("specId") Long specId);

    /**
     * 获取消费者的购物车列表
     * @param shopId
     * @return
     */
    @ApiOperation("获取消费者某间店铺的购物车列表")
    @GetMapping("list/{shopId}")
    Result<List<CartItem>> getCartList(@PathVariable("shopId") String shopId);
}
