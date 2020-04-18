package wang.ismy.blb.impl.product.service;

import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ShopProductDTO;

import java.util.List;

/**
 * 抽象商品目录服务应该提供的接口
 * @author MY
 * @date 2020/4/18 8:29
 */
public interface ProductCategoryService {
    /**
     * 根据目录ID查询商品
     * @param categoryId
     * @return 没有结果会返回空列表
     */
    List<ShopProductDTO> getProductByCategory(Long categoryId);

    /**
     * 根据店铺ID查询目录
     * @param shopId
     * @return 没有结果会返回空列表
     */
    List<ProductCategoryDTO> getCategoryList(Long shopId);

    /**
     * 商家添加商品目录
     * @param token
     * @param productCategoryDTO
     */
    void addCategory(String token,ProductCategoryDTO productCategoryDTO);

    /**
     * 更新商品目录
     * @param token
     * @param productCategoryDTO
     */
    void update(String token, ProductCategoryDTO productCategoryDTO);

    /**
     * 删除商品目录
     * @param token
     * @param categoryId
     */
    void delete(String token, Long categoryId);
}
