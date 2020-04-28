package wang.ismy.blb.impl.rider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.impl.rider.pojo.RiderOrderDO;

/**
 * @author MY
 * @date 2020/4/28 10:03
 */
public interface RiderOrderRepository extends JpaRepository<RiderOrderDO,Long> { }
