package wang.ismy.blb.impl.seller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.seller.pojo.SellerInfoDO;

/**
 * @author MY
 * @date 2020/5/3 9:05
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfoDO,Long> {
}
