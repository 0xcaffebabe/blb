package wang.ismy.blb.impl.rider.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.impl.rider.pojo.RiderOrderDO;

import java.util.Optional;

/**
 * @author MY
 * @date 2020/4/28 10:03
 */
public interface RiderOrderRepository extends JpaRepository<RiderOrderDO,Long> {
    /**
     * 根据订单ID查询
     * @param orderId
     * @return
     */
    Optional<RiderOrderDO> findByOrderId(Long orderId);

    /**
     * 根据骑手ID查询
     * @param orderId
     * @param pageable
     * @return
     */
    Page<RiderOrderDO> findAllByRiderId(Long orderId, Pageable pageable);

    /**
     * 根据订单ID查询是否存在
     * @param orderId
     * @return
     */
    boolean existsByOrderId(Long orderId);

    /**
     * 统计骑手接单数
     * @param riderId
     * @return
     */
    long countByRiderId(Long riderId);
}
