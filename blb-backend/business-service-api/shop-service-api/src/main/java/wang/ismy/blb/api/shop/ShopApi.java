package wang.ismy.blb.api.shop;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.api.shop.pojo.dto.ShopCreateDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoUpdateDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopItemDTO;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;

import java.util.List;
import java.util.Map;

/**
 * @author MY
 * @date 2020/4/9 11:38
 */
@Api(tags = "店铺主服务接口")
@RequestMapping(value = "v1/api",produces = MediaType.APPLICATION_JSON_VALUE)
public interface ShopApi {

    /**
     * 获取附近店铺
     * @param location 经纬度
     * @param pageable
     * @return 店铺分页列表
     */
    @ApiOperation("获取附近店铺")
    @ApiImplicitParam(paramType = "query", name = "location", dataType = "String", required = true, value = "经纬度")
    @GetMapping("vicinity")
    Result<Page<ShopItemDTO>> getNearbyShop(@RequestParam("location") String location,
                                            Pageable pageable);

    /**
     * 根据店铺ID获取店铺信息
     * @param shopId
     * @return 店铺信息DTO
     */
    @ApiOperation("根据店铺ID获取店铺信息")
    @ApiImplicitParam(paramType = "path", name = "shopId", dataType = "Long", required = true, value = "店铺ID")
    @GetMapping("info/{shopId}")
    Result<ShopInfoDTO> getShopInfo(@PathVariable("shopId") Long shopId);

    /**
     * 根据店铺ID列表批量获取店铺信息
     * @param shopIdList
     * @return 店铺信息DTO列表
     */
    @ApiOperation("根据店铺ID列表批量获取店铺信息")
    @GetMapping("info/list")
    Result<Map<Long,ShopInfoDTO>> getShopInfo(@RequestParam("shopIdList") @ApiParam("shopIdList") List<Long> shopIdList);

    /**
     * 添加店铺
     * @param shopCreateDTO
     * @return 返回创建后的店铺ID
     */
    @ApiOperation("添加店铺")
    @PostMapping("")
    Result<Long> addShop(@RequestBody ShopCreateDTO shopCreateDTO);

    /**
     * 更新店铺信息
     * @param shopId
     * @param shopInfoUpdateDTO
     * @return 成功失败结果写入result
     */
    @ApiOperation("更新店铺信息")
    @PutMapping("info/{shopId}")
    Result<Void> updateShopInfo(@PathVariable("shopId") Long shopId,
                                @RequestBody ShopInfoUpdateDTO shopInfoUpdateDTO);

    /**
     * 根据商家ID获取店铺信息
     * @param sellerId
     * @return
     */
    @ApiOperation("根据商家ID获取店铺信息")
    @ApiImplicitParam(paramType = "path", name = "sellerId", dataType = "Long", required = true, value = "商家ID")
    @GetMapping("seller/{sellerId}")
    Result<ShopInfoDTO> getShopBySeller(@PathVariable("sellerId") Long sellerId);
}
