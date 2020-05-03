package wang.ismy.blb.impl.seller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.seller.pojo.SellerDO;

/**
 * @author MY
 * @date 2020/5/3 8:53
 */
public interface SellerRepository extends JpaRepository<SellerDO,Long> {
    boolean existsByUsername(String username);

    SellerDO findByUsername(String username);
}
