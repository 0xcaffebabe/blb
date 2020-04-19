package wang.ismy.blb.impl.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.product.pojo.ProductEvaluationDO;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/19 10:05
 */
public interface ProductEvaluationRepository extends JpaRepository<ProductEvaluationDO,Long> {

    /**
     * 查询某个店铺的所有评价
     * @param shopId
     * @return
     */
    List<ProductEvaluationDO> findAllByShopId(Long shopId);

    /**
     * 分页返回
     * @param shopId
     * @param pageable
     * @return
     */
    Page<ProductEvaluationDO> findAllByShopId(Long shopId, Pageable pageable);
}
