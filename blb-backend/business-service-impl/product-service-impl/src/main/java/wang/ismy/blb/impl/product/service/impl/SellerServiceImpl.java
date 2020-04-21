package wang.ismy.blb.impl.product.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.auth.UserTypeEnum;
import wang.ismy.blb.api.product.pojo.ProductDO;
import wang.ismy.blb.api.product.pojo.ProductSpecDO;
import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductCreateDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductSpecDTO;
import wang.ismy.blb.api.product.pojo.dto.ShopProductDTO;
import wang.ismy.blb.common.BlbException;
import wang.ismy.blb.common.SnowFlake;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.impl.product.client.AuthApiClient;
import wang.ismy.blb.impl.product.client.ShopApiClient;
import wang.ismy.blb.impl.product.repository.ProductCategoryRepository;
import wang.ismy.blb.impl.product.repository.ProductRepository;
import wang.ismy.blb.impl.product.repository.ProductSpecRepository;
import wang.ismy.blb.impl.product.service.ProductSellerService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/4/20 8:41
 */
@Service
@AllArgsConstructor
@Slf4j
public class SellerServiceImpl implements ProductSellerService {
    private final ProductCategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductSpecRepository specRepository;
    private final AuthApiClient authApiClient;
    private final ShopApiClient shopApiClient;
    private final SnowFlake snowFlake;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void addProduct(String token, ProductCreateDTO productCreateDTO) {
        User seller = getSeller(token);
        if (seller == null) {
            return;
        }
        if (getShop(seller)) {
            return;
        }
        if (validProductAndCate(productCreateDTO)) {
            return;
        }
        ProductDO product = new ProductDO();
        BeanUtils.copyProperties(productCreateDTO, product);
        product.setProductId(snowFlake.nextId());
        product.setCreateTime(LocalDateTime.now());
        product.setUpdateTime(LocalDateTime.now());
        // 写入商品主信息
        productRepository.save(product);
        for (ProductSpecDTO specDTO : productCreateDTO.getProductSpecList()) {
            ProductSpecDO spec = new ProductSpecDO();
            BeanUtils.copyProperties(specDTO, spec);
            spec.setSpecId(snowFlake.nextId());
            spec.setProductId(product.getProductId());
            spec.setCreateTime(LocalDateTime.now());
            spec.setUpdateTime(LocalDateTime.now());
            spec.setStock(specDTO.getStock());
            specRepository.save(spec);
        }
    }

    @Override
    public void updateProduct(String token, Long productId, ProductCreateDTO productCreateDTO) {
        User seller = getSeller(token);
        if (seller == null) {
            return;
        }
        if (getShop(seller)) {
            return;
        }
        // 查询分类是否属于当前店铺
        if (categoryRepository.findById(productCreateDTO.getProductCategory()).isEmpty()) {
            log.warn("分类不属于当前店铺");
            return;
        }
        Optional<ProductDO> optional = productRepository.findById(productId);
        if (optional.isEmpty()){
            log.warn("商品不存在:{}",productId);
            return;
        }

        ProductDO product = optional.get();
        product.setProductCategory(productCreateDTO.getProductCategory());
        product.setProductDesc(productCreateDTO.getProductDesc());
        product.setProductName(productCreateDTO.getProductName());
        product.setProductImg(productCreateDTO.getProductImg());
        product.setUpdateTime(LocalDateTime.now());
        // 写入商品主信息
        productRepository.save(product);
        if (CollectionUtils.isEmpty(productCreateDTO.getProductSpecList())){
            return;
        }
        var specList = specRepository.findAllById(
                productCreateDTO.getProductSpecList().stream().map(ProductSpecDTO::getSpecId).collect(Collectors.toList())
        );
        for (ProductSpecDO spec : specList) {
            if (!spec.getProductId().equals(productId)){
                throw new BlbException(ResultCode.PERMISSION_NO_ACCESS);
            }
        }
        for (ProductSpecDTO specDTO : productCreateDTO.getProductSpecList()) {
            ProductSpecDO spec = specRepository.findById(specDTO.getSpecId()).orElse(null);
            if (spec == null){
                throw new BlbException(ResultCode.SPECIFIED_QUESTIONED_USER_NOT_EXIST);
            }
            spec.setSpecName(specDTO.getSpecName());
            spec.setPackageFee(specDTO.getPackageFee());
            spec.setPrice(specDTO.getPrice());
            spec.setUpdateTime(LocalDateTime.now());
            specRepository.save(spec);
        }
    }

    @Override
    public void deleteProduct(String token, Long productId) {
        User seller = getSeller(token);
        if (seller == null) {
            return;
        }
        var shopRes = shopApiClient.getShopBySeller(seller.getUserId());
        if (!shopRes.getSuccess()){
            log.warn("商家删除商品 获取店铺信息失败:{}",shopRes);
            return;
        }
        var shop = shopRes.getData();
        if (shop == null){
            log.info("商家删除商品 找不到当前商家所属店铺");
            return;
        }
        var product = productRepository.findById(productId).orElse(null);
        if (product == null){
            log.warn("商品删除 商品不存在");
            return;
        }
        var category = categoryRepository.findById(product.getProductCategory()).orElse(null);
        if (category == null){
            log.warn("商品删除 商品目录不存在");
            return;
        }
        if (!category.getShopId().equals(shop.getShopId())){
            log.warn("商家删除商品 商品不属于当前商家");
            return;
        }
        product.setRemoved(true);
        productRepository.save(product);
    }

    @Override
    public Page<ShopProductDTO> getProductList(String token, Pageable pageable) {
        User seller = getSeller(token);
        if (seller == null) {
            return new Page<>();
        }
        var shopRes = shopApiClient.getShopBySeller(seller.getUserId());
        if (!shopRes.getSuccess()){
            log.warn("商家商品列表 获取店铺信息失败:{}",shopRes);
            return new Page<>();
        }
        var shop = shopRes.getData();
        if (shop == null){
            log.info("商家商品列表 找不到当前商家所属店铺");
            return new Page<>();
        }
        int page = (int) (pageable.getPage()-1);
        int size = Math.toIntExact(pageable.getSize());
        var cateList = categoryRepository.findAllByShopId(shop.getShopId());
        var dbPage = productRepository.findAllByProductCategoryIn(
                cateList.stream().map(i -> i.getCategoryId()).collect(Collectors.toList()), PageRequest.of(page, size)
        );
        return new Page<>(dbPage.getTotalElements(),dbPage.getContent().stream().map(i->{
            ShopProductDTO dto = new ShopProductDTO();
            BeanUtils.copyProperties(i,dto);
            return dto;
        }).collect(Collectors.toList()));
    }

    @Override
    public List<ProductCategoryDTO> getCategoryList(String token) {
        User seller = getSeller(token);
        if (seller == null) {
            return List.of();
        }
        var shopRes = shopApiClient.getShopBySeller(seller.getUserId());
        if (!shopRes.getSuccess()){
            log.warn("商家商品目录列表 获取店铺信息失败:{}",shopRes);
            return List.of();
        }
        var shop = shopRes.getData();
        if (shop == null){
            log.info("商家商品目录列表 找不到当前商家所属店铺");
            return List.of();
        }
        var cateList = categoryRepository.findAllByShopId(shop.getShopId());
        return cateList.stream()
                .map(i->{
                    ProductCategoryDTO dto = new ProductCategoryDTO();
                    BeanUtils.copyProperties(i,dto);
                    return dto;
                }).collect(Collectors.toList());
    }

    private boolean validProductAndCate(ProductCreateDTO productCreateDTO) {
        if (CollectionUtils.isEmpty(productCreateDTO.getProductSpecList())) {
            log.warn("商家添加商品 没有包含商品规格信息");
            return true;
        }
        // 查询分类是否属于当前店铺
        if (categoryRepository.findById(productCreateDTO.getProductCategory()).isEmpty()) {
            log.warn("分类不属于当前店铺");
            return true;
        }
        return false;
    }

    private boolean getShop(User seller) {
        var shopRes = shopApiClient.getShopBySeller(seller.getUserId());
        if (!shopRes.getSuccess()) {
            log.warn("商家添加商品 获取店铺信息失败:{}", shopRes);
            return true;
        }
        var shop = shopRes.getData();
        if (shop == null) {
            log.warn("当前登录商家没有店铺:");
            return true;
        }
        return false;
    }

    private User getSeller(String token) {
        var authRes = authApiClient.valid(token);
        if (!authRes.getSuccess()) {
            log.warn("商家添加商品 获取商家信息失败:{}", authRes);
            return null;
        }
        var seller = authRes.getData();
        if (!seller.getUserType().equals(UserTypeEnum.SELLER.getType())) {
            log.warn("当前登录用户不是商家");
            return null;
        }
        return seller;
    }
}
