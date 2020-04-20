package wang.ismy.blb.impl.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.product.pojo.ProductDO;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/17 13:39
 */
public interface ProductRepository extends JpaRepository<ProductDO,Long> {
    /**
     * 根据商品目录查询商品
     * @param category
     * @return
     */
    List<ProductDO> findAllByProductCategory(Long category);

    /**
     * 根据商品分类分页查询
     * @param productIdList
     * @param pagable
     * @return
     */
    Page<ProductDO> findAllByProductCategoryIn(List<Long> productIdList, Pageable pagable);
}
