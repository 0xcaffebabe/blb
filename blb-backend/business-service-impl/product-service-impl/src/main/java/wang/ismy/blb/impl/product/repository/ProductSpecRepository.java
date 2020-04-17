package wang.ismy.blb.impl.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.product.pojo.ProductSpecDO;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/17 13:55
 */
public interface ProductSpecRepository extends JpaRepository<ProductSpecDO,Long> {
    /**
     * 根据商品ID查询商品规格
     * @param productId
     * @return
     */
    List<ProductSpecDO> findAllByProductId(Long productId);

    /**
     * 根据商品ID列表批量查询商品规格
     * @param productIdList
     * @return
     */
    List<ProductSpecDO> findAllByProductIdIn(List<Long> productIdList);
}
