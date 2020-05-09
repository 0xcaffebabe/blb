package wang.ismy.blb.aggregation.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.aggregation.client.ShopApiClient;
import wang.ismy.blb.aggregation.client.ShopCategoryApiClient;
import wang.ismy.blb.aggregation.pojo.CategoryShopDTO;
import wang.ismy.blb.aggregation.service.ShopService;
import wang.ismy.blb.api.shop.pojo.dto.ShopCategoryDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopItemDTO;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/5/7 8:45
 */
@RestController
@RequestMapping(value = "category",produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "店铺分类接口")
@AllArgsConstructor
public class ShopCategoryAggApi {
    private final ShopCategoryApiClient shopCategoryApiClient;
    private final ShopApiClient shopApiClient;
    @ApiOperation("获取某一层级的目录")
    @GetMapping("{level}")
    public Result<List<ShopCategoryDTO>> getCategory(@PathVariable Integer level){
        return shopCategoryApiClient.getCategoryByLevel(level);
    }

    @ApiOperation("根据目录ID获取店铺")
    @GetMapping("{categoryId}/shop")
    public Result getShop(@PathVariable Long categoryId,
                                             @RequestParam String location,
                                             @RequestParam(defaultValue = "1") Long page,
                                             @RequestParam(defaultValue = "10") Long size){
        var shopCateRes = shopCategoryApiClient.getShopByCategory(categoryId,location, page,size);
        return new ShopService().convertShopItems(shopCateRes,shopApiClient);
    }
}
