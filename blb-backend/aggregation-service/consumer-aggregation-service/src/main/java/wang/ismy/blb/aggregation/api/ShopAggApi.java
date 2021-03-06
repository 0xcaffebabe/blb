package wang.ismy.blb.aggregation.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.aggregation.client.*;
import wang.ismy.blb.aggregation.client.order.OrderApiClient;
import wang.ismy.blb.aggregation.client.order.OrderConsumerApiClient;
import wang.ismy.blb.aggregation.pojo.CartItemShowDTO;
import wang.ismy.blb.aggregation.service.ShopService;
import wang.ismy.blb.api.cart.CartItem;
import wang.ismy.blb.api.order.pojo.dto.OrderCreateDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderQuery;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderDetailDTO;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderItemDTO;
import wang.ismy.blb.api.product.pojo.dto.*;
import wang.ismy.blb.api.product.pojo.dto.eval.ConsumerEvalItem;
import wang.ismy.blb.api.product.pojo.dto.eval.ShopEvalInfo;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.CurrentRequestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/5/7 19:23
 */
@RestController
@RequestMapping(value = "shop", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "店铺接口")
@AllArgsConstructor
@Slf4j
public class ShopAggApi {
    private final ShopApiClient shopApiClient;
    private final ProductCategoryApiClient productCategoryApiClient;
    private final ProductEvalApiClient productEvalApiClient;
    private final CartApiClient cartApiClient;
    private final OrderApiClient orderApiClient;
    private final OrderConsumerApiClient orderConsumerApiClient;
    private final ProductApiClient productApiClient;
    @ApiOperation("获取附近店铺")
    @GetMapping("vicinity")
    public Result getNearbyShop(@RequestParam String location,
                                @RequestParam(defaultValue = "1") Long page,
                                @RequestParam(defaultValue = "10") Long size) {
        var shopRes = shopApiClient.getNearbyShop(location, page,size);
        return new ShopService().convertShopItems(shopRes, shopApiClient);
    }

    @ApiOperation("获取店铺信息")
    @GetMapping("info/{shopId}")
    public Result<ShopInfoDTO> getShopInfo(@PathVariable Long shopId) {
        return shopApiClient.getShopInfo(shopId);
    }

    @ApiOperation("获取店铺商品分类列表")
    @GetMapping("{shopId}/category")
    public Result<List<ProductCategoryDTO>> getCategoryList(@PathVariable Long shopId) {
        return productCategoryApiClient.getCategoryList(shopId);
    }

    @ApiOperation("根据商品分类获取商品")
    @GetMapping("{shopId}/{categoryId}/product")
    public Result<List<ShopProductDTO>> getProductByCategory(@PathVariable Long shopId,
                                                             @PathVariable Long categoryId) {
        return productCategoryApiClient.getProductByCategory(categoryId);
    }

    @ApiOperation("获取店铺评价信息")
    @GetMapping("{shopId}/evaluation")
    public Result<ShopEvalInfo> getShopEvalInfo(@PathVariable Long shopId) {
        return productEvalApiClient.getShopEvalInfo(shopId);
    }

    @ApiOperation("获取店铺评价列表")
    @GetMapping("{shopId}/evaluation/list")
    public Result<Page<ConsumerEvalItem>> getShopEvalList(@PathVariable Long shopId,
                                                          @RequestParam(defaultValue = "1") Long page,
                                                          @RequestParam(defaultValue = "10") Long size
    ) {
        return productEvalApiClient.getShopEvalList(shopId, page, size);
    }

    @ApiOperation("获取购物车列表")
    @GetMapping("{shopId}/cart")
    public Result<List<CartItem>> getCartList(@PathVariable Long shopId) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        return cartApiClient.getCartList(token, shopId);
    }

    @ApiOperation("清空购物车")
    @DeleteMapping("{shopId}/cart")
    public Result<Void> deleteProductList(@PathVariable Long shopId){
        return cartApiClient.delAllProductList(shopId);
    }
    @ApiOperation("加入购物车")
    @PutMapping("{shopId}/cart/{productId}/{specId}")
    public Result<Void> addProduct(
            @RequestHeader(SystemConstant.TOKEN) String token,
            @PathVariable("productId") Long productId,
            @PathVariable("specId") Long specId,
            @RequestParam("quantity") Long quantity) {
        return cartApiClient.addProduct(token, productId, specId, quantity);
    }

    @ApiOperation("删除购物车商品")
    @DeleteMapping("{shopId}/cart/{productId}/{specId}")
    public Result<Void> deleteProduct(
            @RequestHeader(SystemConstant.TOKEN) String token,
            @PathVariable("productId") Long productId,
            @PathVariable("specId") Long specId) {
        return cartApiClient.deleteProduct(token,productId,specId);
    }

    @ApiOperation("提交订单")
    @PostMapping("order")
    public Result<String> submitOrder(@RequestBody OrderCreateDTO orderCreateDTO){
        return orderApiClient.addOrder(orderCreateDTO);
    }

    @ApiOperation("搜索店铺")
    @GetMapping("search")
    public Result searchShop(@RequestParam("kw") String keyword,
                                                    @RequestParam(defaultValue = "1") Long page,
                                                    @RequestParam(defaultValue = "10") Long size){
        var shopRes =  shopApiClient.searchShop("117,29",keyword,page,size);
        return new ShopService().convertShopItems(shopRes,shopApiClient);
    }

    @ApiOperation("获取订单列表")
    @GetMapping("order")
    public Result<Page<ConsumerOrderItemDTO>> getConsumerOrderList(@RequestParam(defaultValue = "1") Long page,
                                                                   @RequestParam(defaultValue = "10") Long size){
        return orderConsumerApiClient.getConsumerOrderList(page, size);
    }

    @ApiOperation("获取订单详情")
    @GetMapping("order/{orderId}")
    public Result<ConsumerOrderDetailDTO> getConsumerOrderDetail(@PathVariable Long orderId){
        return orderConsumerApiClient.getConsumerOrderDetail(orderId);
    }
}
