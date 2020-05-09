package wang.ismy.blb.impl.shop.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.api.shop.ShopApi;
import wang.ismy.blb.api.shop.pojo.dto.ShopCreateDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoUpdateDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopItemDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.CurrentRequestUtils;
import wang.ismy.blb.impl.shop.service.ShopService;

import java.util.List;
import java.util.Map;

/**
 * @author MY
 * @date 2020/4/23 8:39
 */
@RestController
@AllArgsConstructor
public class ShopApiImpl implements ShopApi {
    private final ShopService shopService;
    @Override
    public Result<Page<ShopItemDTO>> getNearbyShop(String location, Long page,Long size) {
        return Result.success(shopService.getNearbyShop(location,Pageable.of(page, size)));
    }

    @Override
    public Result<ShopInfoDTO> getShopInfo(Long shopId) {
        return Result.success(shopService.getShopInfo(shopId));
    }

    @Override
    public Result<Map<Long,ShopInfoDTO>> getShopInfo(List<Long> shopIdList) {
        return Result.success(shopService.getShopInfo(shopIdList));
    }

    @Override
    public Result<Long> addShop(ShopCreateDTO shopCreateDTO) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        return Result.success(shopService.addShop(token,shopCreateDTO));
    }

    @Override
    public Result<Void> updateShopInfo(Long shopId, ShopInfoUpdateDTO shopInfoUpdateDTO) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        shopService.updateShopInfo(token,shopId,shopInfoUpdateDTO);
        return Result.success();
    }

    @Override
    public Result<ShopInfoDTO> getShopBySeller(Long sellerId) {
        return Result.success(shopService.getShopBySeller(sellerId));
    }
}
