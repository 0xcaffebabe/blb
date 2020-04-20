package wang.ismy.blb.impl.product.service;

import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductCreateDTO;
import wang.ismy.blb.api.product.pojo.dto.ShopProductDTO;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;

import java.util.List;

/**
 * 商品卖家服务应该提供的接口
 * @author MY
 * @date 2020/4/20 8:22
 */
public interface ProductSellerService {
    /**
     * 商家添加商品
     * @param token
     * @param productCreateDTO
     */
    void addProduct(String token, ProductCreateDTO productCreateDTO);

    /**
     * 商家更新商品
     * @param token
     * @param productId
     * @param productCreateDTO
     */
    void updateProduct(String token,Long productId, ProductCreateDTO productCreateDTO);

    /**
     * 商家删除商品
     * @param token
     * @param productId
     */
    void deleteProduct(String token, Long productId);

    /**
     * 商家获取自己的商品列表
     * @param token
     * @param pageable
     * @return
     */
    Page<ShopProductDTO> getProductList(String token, Pageable pageable);

    /**
     * 商家获取自己的目录
     * @param token
     * @return
     */
    List<ProductCategoryDTO> getCategoryList(String token);
}
