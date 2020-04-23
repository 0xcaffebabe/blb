package wang.ismy.blb.impl.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.shop.pojo.ShopCategoryDO;

/**
 * @author MY
 * @date 2020/4/23 10:31
 */
public interface ShopCategoryRepository extends JpaRepository<ShopCategoryDO,Long> { }
