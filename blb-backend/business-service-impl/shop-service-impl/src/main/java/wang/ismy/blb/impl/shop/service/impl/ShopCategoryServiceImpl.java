package wang.ismy.blb.impl.shop.service.impl;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import wang.ismy.blb.api.shop.pojo.ShopCategoryDO;
import wang.ismy.blb.api.shop.pojo.ShopDO;
import wang.ismy.blb.api.shop.pojo.ShopInfoDO;
import wang.ismy.blb.api.shop.pojo.dto.ShopCategoryDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopItemDTO;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.impl.shop.client.ProductEvalApiClient;
import wang.ismy.blb.impl.shop.repository.ShopCategoryRepository;
import wang.ismy.blb.impl.shop.repository.ShopInfoRepository;
import wang.ismy.blb.impl.shop.repository.ShopRepository;
import wang.ismy.blb.impl.shop.service.ShopCategoryService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/4/23 11:24
 */
@Service
@AllArgsConstructor
@Slf4j
@Setter
public class ShopCategoryServiceImpl implements ShopCategoryService {
    private final ShopCategoryRepository categoryRepository;
    private final ShopRepository shopRepository;
    private final ShopInfoRepository shopInfoRepository;
    private ProductEvalApiClient evalApiClient;

    @Override
    public List<ShopCategoryDTO> getCategoryByLevel(Integer level) {
        List<ShopCategoryDO> list = categoryRepository.findAll();
        var map = list.stream()
                .collect(Collectors.toMap(ShopCategoryDO::getCategoryId, cate -> {
                    ShopCategoryDTO dto = new ShopCategoryDTO();
                    BeanUtils.copyProperties(cate, dto);
                    return dto;
                }));
        var dtoList = new ArrayList<>(map.values());
        dtoList.forEach(dto -> {
            ShopCategoryDTO parent = map.get(dto.getParentId());
            if (parent != null) {
                parent.append(dto);
            }
        });
        return dtoList.stream().filter(dto -> dto.getCategoryLevel().equals(1)).collect(Collectors.toList());
    }

    @Override
    public Page<ShopItemDTO> getShopByCategory(Long categoryId, String location, Pageable pageable) {
        var dbPage = shopRepository.findByCategoryId(categoryId, PageRequest.of(pageable.getPage().intValue() - 1, pageable.getSize().intValue()));
        var shopIdList = dbPage
                .stream()
                .map(ShopDO::getShopId).collect(Collectors.toList());
        var evalRes = evalApiClient.getShopEval(dbPage.stream().map(ShopDO::getShopId).collect(Collectors.toList()));
        if (!evalRes.getSuccess()) {
            log.warn("获取店铺评分失败:{}", evalRes);
        }
        var list = shopInfoRepository.findAllById(shopIdList)
                .stream()
                .map(shopInfo -> {
                    ShopItemDTO dto = new ShopItemDTO();
                    dto.setShopId(shopInfo.getShopId());
                    dto.setShopName(shopInfo.getShopName());
                    dto.setShopLogo(shopInfo.getShopLogo());
                    dto.setDistance(new BigDecimal("2"));
                    dto.setRanking(evalRes.getData().get(dto.getShopId()));
                    return dto;
                }).collect(Collectors.toList());
        return new Page<>(dbPage.getTotalElements(), list);
    }
}
