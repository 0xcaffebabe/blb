package wang.ismy.blb.api.shop;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wang.ismy.blb.api.shop.pojo.dto.ShopCategoryDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopItemDTO;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/9 11:48
 */
@Api(tags = "店铺目录服务接口")
@RequestMapping("category")
public interface ShopCategoryApi {

    /**
     * 获取某一层级的目录
     * @param level 层级
     * @return 目录列表
     */
    @ApiOperation("获取某一层级的目录")
    @ApiImplicitParam(paramType = "path", name = "level", dataType = "Integer", required = true, value = "目录层级")
    @GetMapping("{level}")
    Result<List<ShopCategoryDTO>> getCategoryByLevel(@PathVariable("level") Integer level);

    /**
     * 根据目录ID获取店铺
     * @param categoryId
     * @param location
     * @param pageable
     * @return 店铺分页列表
     */
    @ApiOperation("根据目录ID获取店铺")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "categoryId", dataType = "Long", required = true, value = "目录ID"),
            @ApiImplicitParam(paramType = "query", name = "location", dataType = "String", required = true, value = "经纬度")
    })
    @GetMapping("{categoryId}/shop")
    Result<Page<ShopItemDTO>> getShopByCategory(@PathVariable("categoryId") String categoryId,
                                                @RequestParam("location") String location,
                                                Pageable pageable);


}
