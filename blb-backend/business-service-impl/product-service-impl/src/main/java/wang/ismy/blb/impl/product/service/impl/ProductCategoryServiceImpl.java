package wang.ismy.blb.impl.product.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.auth.UserTypeEnum;
import wang.ismy.blb.api.product.pojo.ProductCategoryDO;
import wang.ismy.blb.api.product.pojo.ProductDO;
import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductDTO;
import wang.ismy.blb.api.product.pojo.dto.ShopProductDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.common.SnowFlake;
import wang.ismy.blb.impl.product.client.AuthApiClient;
import wang.ismy.blb.impl.product.client.OrderApiClient;
import wang.ismy.blb.impl.product.client.ShopApiClient;
import wang.ismy.blb.impl.product.repository.ProductCategoryRepository;
import wang.ismy.blb.impl.product.repository.ProductRepository;
import wang.ismy.blb.impl.product.service.ProductCategoryService;
import wang.ismy.blb.impl.product.service.ProductEvalService;
import wang.ismy.blb.impl.product.service.ProductService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/4/18 8:57
 */
@Service
@AllArgsConstructor
@Slf4j
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final OrderApiClient orderApiClient;
    private final ProductEvalService evalService;
    private final AuthApiClient authApiClient;
    private final ShopApiClient shopApiClient;
    private final SnowFlake snowFlake;

    @Override
    public List<ShopProductDTO> getProductByCategory(Long categoryId) {
        var list = productRepository.findAllByProductCategory(categoryId);
        List<Long> productIdList = list.stream().map(ProductDO::getProductId).collect(Collectors.toList());
        List<ShopProductDTO> productList = productService.getProductList(productIdList)
                .stream()
                .map(productDTO -> {
                    ShopProductDTO dto = new ShopProductDTO();
                    BeanUtils.copyProperties(productDTO, dto);
                    return dto;
                }).collect(Collectors.toList());

        // 添加销量属性
        var orderRemoteRes = orderApiClient.getProductSales(productIdList);
        if (orderRemoteRes.getSuccess()) {
            var salesMap = orderRemoteRes.getData();
            for (ShopProductDTO dto : productList) {
                dto.setSales(salesMap.get(dto.getProductId()));
            }
        }
        // 添加好评率属性
        var ratingMap = evalService.getPositiveRating(productIdList);
        for (ShopProductDTO dto : productList) {
            dto.setPositiveRate(ratingMap.get(dto.getProductId()));
        }
        return productList;
    }

    @Override
    public List<ProductCategoryDTO> getCategoryList(Long shopId) {
        return categoryRepository.findAllByShopId(shopId)
                .stream()
                .map(categoryDO -> {
                    ProductCategoryDTO dto = new ProductCategoryDTO();
                    BeanUtils.copyProperties(categoryDO, dto);
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public void addCategory(String token, ProductCategoryDTO productCategoryDTO) {
        User seller = getSeller(token);
        if (seller == null) {
            return;
        }
        // 根据商家ID获取店铺信息
        ShopInfoDTO shopInfo = getShopInfo(seller);
        if (shopInfo == null) {
            return;
        }
        ProductCategoryDO categoryDO = new ProductCategoryDO();
        categoryDO.setShopId(shopInfo.getShopId());
        categoryDO.setCategoryDesc(productCategoryDTO.getCategoryDesc());
        categoryDO.setCategoryName(productCategoryDTO.getCategoryName());
        categoryDO.setCategoryId(snowFlake.nextId());
        categoryDO.setCreateTime(LocalDateTime.now());
        categoryDO.setUpdateTime(LocalDateTime.now());

        try {
            categoryRepository.save(categoryDO);
        } catch (Exception e) {
            log.warn("增加商品目录 插入商品目录失败,{}", e.getMessage());
        }
    }

    @Override
    public void update(String token, ProductCategoryDTO productCategoryDTO) {
        ProductCategoryDO categoryDO = validCateOfSeller(token, productCategoryDTO.getCategoryId());
        if (categoryDO == null) {
            return;
        }

        categoryDO.setCategoryDesc(productCategoryDTO.getCategoryDesc());
        categoryDO.setCategoryName(productCategoryDTO.getCategoryName());
        categoryDO.setUpdateTime(LocalDateTime.now());

        try {
            categoryRepository.save(categoryDO);
        } catch (Exception e) {
            log.warn("更新商品目录失败,{}", categoryDO);
        }
    }

    @Override
    public void delete(String token, Long categoryId) {
        ProductCategoryDO categoryDO = validCateOfSeller(token, categoryId);
        if (categoryDO == null) {
            return;
        }

        try{
            categoryRepository.deleteById(categoryId);
        }catch (Exception e){
            log.warn("删除商品({})失败:{}",categoryDO,e.getMessage());
        }
    }

    private ProductCategoryDO validCateOfSeller(String token, Long categoryId) {
        User seller = getSeller(token);
        if (seller == null) {
            return null;
        }
        // 根据商家ID获取店铺信息
        ShopInfoDTO shopInfo = getShopInfo(seller);
        if (shopInfo == null) {
            return null;
        }
        ProductCategoryDO categoryDO =
                categoryRepository.findById(categoryId).orElse(null);
        if (categoryDO == null) {
            log.warn("商品分类不存在，{}", categoryDO);
            return null;
        }
        if (!categoryDO.getShopId().equals(shopInfo.getShopId())) {
            log.warn("该商品分类({})不属于该商家({})", categoryDO, seller);
            return null;
        }
        return categoryDO;
    }

    private User getSeller(String token) {
        var authRes = authApiClient.valid(token);
        if (!authRes.getSuccess()) {
            log.warn("增加商品目录获取商家信息失败,{}", authRes);
            return null;
        }
        var user = authRes.getData();
        if (user == null || !user.getUserType().equals(UserTypeEnum.SELLER.getType())) {
            log.warn("增加商品目录 商家不存在或者不是商家,{}", user);
            return null;
        }
        return user;
    }

    private ShopInfoDTO getShopInfo(User user) {
        var shopRes = shopApiClient.getShopBySeller(user.getUserId());
        if (!shopRes.getSuccess()) {
            log.warn("增加商品目录 获取商家店铺失败,{}", shopRes);
            return null;
        }
        var shopInfo = shopRes.getData();
        if (shopInfo == null) {
            log.warn("增加商品目录 获取商家店铺失败");
            return null;
        }
        return shopInfo;
    }
}
