package wang.ismy.blb.aggregation.api;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.aggregation.client.ProductCategoryApiClient;
import wang.ismy.blb.aggregation.client.ShopApiClient;
import wang.ismy.blb.aggregation.client.ShopCategoryApiClient;
import wang.ismy.blb.aggregation.service.ShopService;
import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ShopProductDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;

import java.util.List;

/**
 * @author MY
 * @date 2020/5/7 19:23
 */
@RestController
@RequestMapping(value = "shop",produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "店铺接口")
@AllArgsConstructor
public class ShopAggApi {
    private final ShopApiClient shopApiClient;
    private final ProductCategoryApiClient productCategoryApiClient;
    @GetMapping("vicinity")
    public Result getNearbyShop(@RequestParam String location,
                                @RequestParam(defaultValue = "1") Long page,
                                @RequestParam(defaultValue = "10") Long size){
        var shopRes = shopApiClient.getNearbyShop(location, Pageable.of(page,size));
        return new ShopService().convertShopItems(shopRes,shopApiClient);
    }

    @GetMapping("info/{shopId}")
    public Result<ShopInfoDTO> getShopInfo(@PathVariable Long shopId) {
        return shopApiClient.getShopInfo(shopId);
    }

    @GetMapping("{shopId}/category")
    public Result<List<ProductCategoryDTO>> getCategoryList(@PathVariable Long shopId){
        return productCategoryApiClient.getCategoryList(shopId);
    }

    @GetMapping("{shopId}/{categoryId}/product")
    public Result<List<ShopProductDTO>> getProductByCategory(@PathVariable Long shopId,
                                                             @PathVariable Long categoryId){
        return productCategoryApiClient.getProductByCategory(categoryId);
    }
}
