package wang.ismy.blb.impl.product.service;

import wang.ismy.blb.api.product.pojo.dto.CartProductGetDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductSpecDTO;

import java.util.List;

/**
 * 商品服务应该提供的接口
 * @author MY
 * @date 2020/4/17 8:40
 */
public interface ProductService {
    /**
     * 根据商品ID查询商品
     * @param productId
     * @return 不存在返回null
     */
    ProductDTO getProduct(Long productId);

    /**
     * 根据商品ID列表批量获取
     * @param productIdList
     * @return 返回列表 即使没有元素
     */
    List<ProductDTO> getProductList(List<Long> productIdList);

    /**
     * 根据商品ID与规格ID列表拉取商品信息
     * @param list
     * @return
     */
    List<ProductDTO> getProductAndSpecList(List<CartProductGetDTO> list);

    /**
     * 根据商品规格ID获取商品规格
     * @param specId
     * @return
     */
    ProductSpecDTO getProductSpec(Long specId);


    /**
     * 更新库存
     * @param token
     * @param specId
     * @param stock
     */
    void updateStock(String token,Long specId,Long stock);
}
