package wang.ismy.blb.impl.shop.service;

import wang.ismy.blb.api.shop.pojo.ShopDO;
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
 * 定义商品服务提供的接口
 * @author MY
 * @date 2020/4/23 8:41
 */
public interface ShopService {
    /**
     * 根据传入的经纬度获取附近的商家
     * @param location
     * @param pageable
     * @return
     */
    Page<ShopItemDTO> getNearbyShop(String location, Pageable pageable);

    /**
     * 根据店铺ID获取店铺信息
     * @param shopId
     * @return
     */
    ShopInfoDTO getShopInfo(Long shopId);

    /**
     * 根据店铺ID列表批量获取店铺信息
     * @param shopIdList
     * @return
     */
    Map<Long,ShopInfoDTO> getShopInfo(List<Long> shopIdList);

    /**
     * 当前登录商家增加店铺
     * @param token
     * @param shopCreateDTO
     * @return 店铺ID
     */
    String addShop(String token,ShopCreateDTO shopCreateDTO);

    /**
     * 当前商家更新店铺信息
     * @param token
     * @param shopId
     * @param shopInfoUpdateDTO
     */
    void updateShopInfo(String token, Long shopId, ShopInfoUpdateDTO shopInfoUpdateDTO);

    /**
     * 根据商家ID获取店铺
     * @param sellerId
     * @return
     */
    ShopInfoDTO getShopBySeller(Long sellerId);

    /**
     * 搜索店铺
     * @param location
     * @param kw
     * @param page
     * @return
     */
    Page<ShopItemDTO> searchShop(String location, String kw, Pageable page);

    /**
     * 查询全部店铺数据
     * @return 店铺列表
     */
    List<ShopDO> findAll();
}
