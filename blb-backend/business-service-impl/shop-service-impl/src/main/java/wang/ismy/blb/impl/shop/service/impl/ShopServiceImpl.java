package wang.ismy.blb.impl.shop.service.impl;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.shop.pojo.ShopDO;
import wang.ismy.blb.api.shop.pojo.ShopInfoDO;
import wang.ismy.blb.api.shop.pojo.dto.ShopCreateDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoUpdateDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopItemDTO;
import wang.ismy.blb.common.BlbException;
import wang.ismy.blb.common.SnowFlake;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.impl.shop.client.AuthApiClient;
import wang.ismy.blb.impl.shop.client.ProductEvalApiClient;
import wang.ismy.blb.impl.shop.client.SellerApiClient;
import wang.ismy.blb.impl.shop.repository.ShopCategoryRepository;
import wang.ismy.blb.impl.shop.repository.ShopInfoRepository;
import wang.ismy.blb.impl.shop.repository.ShopRepository;
import wang.ismy.blb.impl.shop.service.ShopService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/4/23 9:34
 */
@Service
@AllArgsConstructor
@Setter
@Slf4j
public class ShopServiceImpl implements ShopService {
    private final ShopInfoRepository shopInfoRepository;
    private final ShopRepository shopRepository;
    private final ShopCategoryRepository categoryRepository;
    private ProductEvalApiClient productEvalApiClient;
    private SellerApiClient sellerApiClient;
    private AuthApiClient authApiClient;
    private final SnowFlake snowFlake;
    @Override
    public Page<ShopItemDTO> getNearbyShop(String location, Pageable pageable) {
        var dbPage = shopInfoRepository.findAll(PageRequest.of(pageable.getPage().intValue()-1,pageable.getSize().intValue()));
        var evalRes = productEvalApiClient.getShopEval(dbPage.stream().map(ShopInfoDO::getShopId).collect(Collectors.toList()));
        if (!evalRes.getSuccess()){
            log.warn("获取店铺评分失败:{}",evalRes);
        }
        var list = dbPage.getContent().stream().map(shopInfo -> {
            ShopItemDTO dto = new ShopItemDTO();
            dto.setShopId(shopInfo.getShopId());
            dto.setShopName(shopInfo.getShopName());
            dto.setShopLogo(shopInfo.getShopLogo());
            dto.setDistance(new BigDecimal("2"));
            dto.setRanking(evalRes.getData().get(dto.getShopId()));
            return dto;
        }).collect(Collectors.toList());
        return new Page<>(dbPage.getTotalElements(),list);
    }

    @Override
    public ShopInfoDTO getShopInfo(Long shopId) {
        return getShopById(shopId);
    }

    @Override
    public Map<Long, ShopInfoDTO> getShopInfo(List<Long> shopIdList) {
        return shopInfoRepository.findAllById(shopIdList)
                .stream()
                .collect(Collectors.toMap(ShopInfoDO::getShopId,infoDO->{
                    ShopInfoDTO dto = new ShopInfoDTO();
                    BeanUtils.copyProperties(infoDO,dto);
                    return dto;
                }));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String addShop(String token, ShopCreateDTO shopCreateDTO) {
        User seller = getSeller(token);
        if (!categoryRepository.existsById(shopCreateDTO.getCategoryId())){
            log.warn("添加店铺 商品目录不存在:{}",shopCreateDTO.getCategoryId());
            throw new BlbException("商品目录不存在");
        }
        ShopDO shopDO = shopRepository.findBySellerId(seller.getUserId());
        if (shopDO != null){
            return shopDO.getShopId().toString();
        }
        shopDO = new ShopDO();
        shopDO.setCategoryId(shopCreateDTO.getCategoryId());
        shopDO.setSellerId(seller.getUserId());
        shopDO.initTime();
        shopDO.setShopId(snowFlake.nextId());
        shopRepository.save(shopDO);

        ShopInfoDO shopInfoDO = new ShopInfoDO();
        BeanUtils.copyProperties(shopCreateDTO,shopInfoDO);
        shopInfoDO.setShopId(shopDO.getShopId());
        shopInfoRepository.save(shopInfoDO);
        return shopDO.getShopId().toString();
    }

    private User getSeller(String token) {
        var authRes = authApiClient.valid(token);
        if (!authRes.getSuccess()){
            log.warn("获取商家信息失败:{}",authRes);
            throw new BlbException("获取商家信息失败");
        }
        return authRes.getData();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateShopInfo(String token, Long shopId, ShopInfoUpdateDTO shopInfoUpdateDTO) {
        var seller = getSeller(token);
        var shop = shopRepository.findById(shopId).orElseThrow();
        if (!shop.getSellerId().equals(seller.getUserId())){
            log.warn("店铺{}不属于商家{}",shop.getShopId(),seller.getUserId());
            throw new BlbException("店铺不属于商家");
        }
        var shopInfo = shopInfoRepository.findById(shopId).orElseThrow();

        if (shopInfoUpdateDTO.getCategoryId() != null){
            if (!categoryRepository.existsById(shopInfoUpdateDTO.getCategoryId())){
                log.warn("店铺分类{}不存在",shopInfoUpdateDTO.getCategoryId());
                throw new BlbException("店铺分类不存在");
            }
            shop.setCategoryId(shopInfoUpdateDTO.getCategoryId());
            shopRepository.save(shop);
        }
        BeanUtils.copyProperties(shopInfoUpdateDTO,shopInfo);
        shopInfoRepository.save(shopInfo);
    }

    @Override
    public ShopInfoDTO getShopBySeller(Long sellerId) {
        var shop = shopRepository.findBySellerId(sellerId);
        if (shop == null){
            return null;
        }
        return getShopById(shop.getShopId());
    }

    private ShopInfoDTO getShopById(Long shopId) {
        ShopInfoDO shopInfoDO = shopInfoRepository.findById(shopId).orElseThrow(()->new BlbException("店铺不存在"));
        ShopDO shopDO = shopRepository.findById(shopId).orElseThrow();
        ShopInfoDTO dto = new ShopInfoDTO();
        BeanUtils.copyProperties(shopInfoDO,dto);

        var sellerRes = sellerApiClient.getSellerInfo(shopDO.getSellerId());
        if (!sellerRes.getSuccess()){
            log.warn("获取商家信息失败:{}",sellerRes);
        }else {
            dto.setSellerName(sellerRes.getData().getRealName());
            dto.setPhone(sellerRes.getData().getPhone());
        }
        dto.setSellerId(shopDO.getSellerId());
        return dto;
    }
}
