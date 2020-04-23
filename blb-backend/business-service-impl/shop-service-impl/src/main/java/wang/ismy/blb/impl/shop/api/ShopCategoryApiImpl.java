package wang.ismy.blb.impl.shop.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.api.shop.ShopCategoryApi;
import wang.ismy.blb.api.shop.pojo.dto.ShopCategoryDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopItemDTO;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.impl.shop.service.ShopCategoryService;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/23 11:07
 */
@RestController
@AllArgsConstructor
public class ShopCategoryApiImpl implements ShopCategoryApi {
    private final ShopCategoryService shopCategoryService;
    @Override
    public Result<List<ShopCategoryDTO>> getCategoryByLevel(Integer level) {
        return Result.success(shopCategoryService.getCategoryByLevel(level));
    }

    @Override
    public Result<Page<ShopItemDTO>> getShopByCategory(Long categoryId, String location, Pageable pageable) {
        return Result.success(shopCategoryService.getShopByCategory(categoryId,location,pageable));
    }
}
