package wang.ismy.blb.impl.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.shop.pojo.ShopInfoDO;

/**
 * @author MY
 * @date 2020/4/23 9:52
 */
public interface ShopInfoRepository extends JpaRepository<ShopInfoDO,Long> { }
