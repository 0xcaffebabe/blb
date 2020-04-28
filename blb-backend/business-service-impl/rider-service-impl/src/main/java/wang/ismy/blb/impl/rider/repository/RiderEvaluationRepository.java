package wang.ismy.blb.impl.rider.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.rider.pojo.entity.RiderEvaluationDO;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/28 10:01
 */
public interface RiderEvaluationRepository extends JpaRepository<RiderEvaluationDO,Long> {

    /**
     * 根据骑手ID统计
     * @param riderId
     * @return
     */
    long countByRiderId(Long riderId);

    /**
     * 根据订单ID与骑手ID查询存在
     * @param orderId
     * @param riderId
     * @return
     */
    boolean existsByOrderIdAndRiderId(Long orderId,Long riderId);

    /**
     * 查询骑手的评价
     * @param riderId
     * @return
     */
    List<RiderEvaluationDO> findByRiderId(Long riderId);

    /**
     * 分页查询骑手评价
     * @param riderId
     * @param pageable
     * @return
     */
    Page<RiderEvaluationDO> findByRiderId(Long riderId, Pageable pageable);
}
