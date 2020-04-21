package wang.ismy.blb.impl.product.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import wang.ismy.blb.api.product.pojo.ProductCategoryDO;
import wang.ismy.blb.api.product.pojo.ProductDO;
import wang.ismy.blb.api.product.pojo.ProductSpecDO;
import wang.ismy.blb.api.product.pojo.dto.CartProductGetDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductSpecDTO;
import wang.ismy.blb.impl.product.pojo.ProductStockDO;
import wang.ismy.blb.impl.product.repository.ProductCategoryRepository;
import wang.ismy.blb.impl.product.repository.ProductRepository;
import wang.ismy.blb.impl.product.repository.ProductSpecRepository;
import wang.ismy.blb.impl.product.repository.ProductStockRepository;
import wang.ismy.blb.impl.product.service.ProductService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/4/17 10:11
 */
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductSpecRepository productSpecRepository;

    @Override
    public ProductDTO getProduct(Long productId) {
        // 获取商品主信息
        ProductDO product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return null;
        }
        // 获取商品目录信息
        ProductCategoryDO productCategory =
                productCategoryRepository.findById(product.getProductCategory()).orElse(null);
        // 获取商品规格信息
        List<ProductSpecDO> productSpecList = productSpecRepository.findAllByProductId(productId);

        ProductDTO productDTO = new ProductDTO();
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
        BeanUtils.copyProperties(product, productDTO);
        productDTO.setProductCategory(productCategoryDTO);
        if (productCategory != null) {
            BeanUtils.copyProperties(productCategory, productCategoryDTO);
        }
        productDTO.setProductSpecList(
                productSpecList.stream()
                        .map(spec -> {
                            ProductSpecDTO specDTO = new ProductSpecDTO();
                            BeanUtils.copyProperties(spec, specDTO);
                            return specDTO;
                        }).collect(Collectors.toList())
        );
        return productDTO;
    }

    @Override
    public List<ProductDTO> getProductList(List<Long> productIdList) {
        // 根据商品ID列表获取商品列表
        List<ProductDO> productList = productRepository.findAllById(productIdList);
        Map<Long, ProductDTO> productMap = new HashMap<>(productIdList.size());
        for (ProductDO productDO : productList) {
            ProductDTO dto = new ProductDTO();
            BeanUtils.copyProperties(productDO, dto);
            productMap.put(productDO.getProductId(), dto);
        }
        // 根据商品列表获取商品目录列表
        List<ProductCategoryDO> categoryList = productCategoryRepository
                .findAllById(
                        productList.stream()
                                .map(ProductDO::getProductCategory)
                                .collect(Collectors.toList())
                );
        Map<Long, ProductCategoryDTO> categoryMap = new HashMap<>();
        for (ProductCategoryDO categoryDO : categoryList) {
            ProductCategoryDTO categoryDTO = new ProductCategoryDTO();
            BeanUtils.copyProperties(categoryDO, categoryDTO);
            categoryMap.put(categoryDO.getCategoryId(), categoryDTO);
        }
        // 将商品目录项赋值给product
        for (ProductDO productDO : productList) {
            ProductCategoryDTO productCategoryDTO = categoryMap.get(productDO.getProductCategory());
            productMap.get(productDO.getProductId()).setProductCategory(productCategoryDTO);
        }
        // 根据商品ID列表获取规格列表
        List<ProductSpecDO> specList = productSpecRepository.findAllByProductIdIn(productIdList);
        // 将规格项分配给product
        for (ProductSpecDO specDO : specList) {
            ProductDTO productDTO = productMap.get(specDO.getProductId());
            ProductSpecDTO specDTO = new ProductSpecDTO();
            BeanUtils.copyProperties(specDO, specDTO);
            productDTO.appendSpec(specDTO);
        }
        return new ArrayList<>(productMap.values());
    }

    @Override
    public List<ProductDTO> getProductAndSpecList(List<CartProductGetDTO> cartGetList) {
        var list = getProductList(
                cartGetList.stream()
                        .map(CartProductGetDTO::getProductId).collect(Collectors.toList())
        );
        // 根据参数格式对返回结果重新排列
        Map<Long, ProductSpecDTO> specMap = new HashMap<>();
        for (ProductDTO productDTO : list) {
            if (CollectionUtils.isEmpty(productDTO.getProductSpecList())) {
                continue;
            }
            for (ProductSpecDTO specDTO : productDTO.getProductSpecList()) {
                specMap.put(specDTO.getSpecId(), specDTO);
            }
        }
        Map<Long, ProductDTO> productMap =
                list.stream().collect(Collectors.toMap(
                        ProductDTO::getProductId,
                        productDTO -> productDTO)
                );
        List<ProductDTO> resultList = new ArrayList<>();
        for (CartProductGetDTO dto : cartGetList) {
            ProductDTO tmp = productMap.get(dto.getProductId());
            ProductDTO product = new ProductDTO();
            BeanUtils.copyProperties(tmp,product);
            product.setProductSpecList(new ArrayList<>());
            product.appendSpec(specMap.get(dto.getSpecId()));
            resultList.add(product);
        }
        return resultList;
    }

    @Override
    public ProductSpecDTO getProductSpec(Long specId) {
        ProductSpecDO productSpecDO = productSpecRepository.findById(specId).orElse(null);
        if (productSpecDO == null) {
            return null;
        }
        ProductSpecDTO dto = new ProductSpecDTO();
        BeanUtils.copyProperties(productSpecDO, dto);
        return dto;
    }
}
