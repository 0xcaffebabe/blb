package wang.ismy.blb.impl.shop.service;

import wang.ismy.blb.api.shop.pojo.dto.ShopCategoryDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopItemDTO;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/23 11:10
 */
public interface ShopCategoryService {
    /**
     * 根据目录层级获取目录
     * @param level 等级为0代表获取全部目录
     * @return
     */
    List<ShopCategoryDTO> getCategoryByLevel(Integer level);

    /**
     * 根据目录获取店铺
     * @param categoryId
     * @param location
     * @param pageable
     * @return
     */
    Page<ShopItemDTO> getShopByCategory(Long categoryId, String location, Pageable pageable);
}
