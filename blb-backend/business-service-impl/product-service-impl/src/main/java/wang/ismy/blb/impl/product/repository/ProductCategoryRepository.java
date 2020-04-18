package wang.ismy.blb.impl.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.product.pojo.ProductCategoryDO;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/17 13:52
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryDO,Long> {
    /**
     * 根据店铺ID获取商品分类
     * @param shopId
     * @return
     */
    List<ProductCategoryDO> findAllByShopId(Long shopId);
}
