package wang.ismy.blb.impl.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.shop.pojo.ShopDO;
import wang.ismy.blb.api.shop.pojo.ShopInfoDO;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/23 9:35
 */
public interface ShopRepository extends JpaRepository<ShopDO,Long> {
    /**
     * 根据商家ID查询
     * @param sellerId
     * @return
     */
    ShopDO findBySellerId(Long sellerId);

    /**
     * 根据目录ID查询店铺
     * @param categoryId
     * @param pageable
     * @return
     */
    Page<ShopDO> findByCategoryId(Long categoryId, Pageable pageable);
}
