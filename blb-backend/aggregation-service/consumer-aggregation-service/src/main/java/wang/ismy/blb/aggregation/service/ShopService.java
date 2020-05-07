package wang.ismy.blb.aggregation.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import wang.ismy.blb.aggregation.client.ShopApiClient;
import wang.ismy.blb.aggregation.pojo.CategoryShopDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopItemDTO;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Result;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/5/7 19:28
 */
@AllArgsConstructor
public class ShopService {

    public Result convertShopItems(Result<Page<ShopItemDTO>> result, ShopApiClient shopApiClient){
        if (!result.getSuccess()){
            return result;
        }
        List<ShopItemDTO> shopList = result.getData().getData();
        if (CollectionUtils.isEmpty(shopList)){
            return result;
        }

        var shopRes = shopApiClient.getShopInfo(
                shopList.stream()
                        .map(ShopItemDTO::getShopId).collect(Collectors.toList())
        );
        if (!shopRes.getSuccess()){
            return result;
        }

        var shopMap = shopRes.getData();
        var resultList = shopList.stream()
                .map(shopItemDTO -> {
                    CategoryShopDTO shopDTO = new CategoryShopDTO();
                    BeanUtils.copyProperties(shopItemDTO,shopDTO);
                    var shop = shopMap.get(shopItemDTO.getShopId());
                    if (shop == null){
                        return shopDTO;
                    }
                    shopDTO.setDeliveryTime("38分钟");
                    shopDTO.setStartingPrice(shop.getStartingPrice());
                    shopDTO.setDeliveryFee(shop.getDeliveryFee());
                    shopDTO.setSales(25);
                    return shopDTO;
                }).collect(Collectors.toList());

        return Result.success(new Page<>(result.getData().getTotal(),resultList));
    }
}
