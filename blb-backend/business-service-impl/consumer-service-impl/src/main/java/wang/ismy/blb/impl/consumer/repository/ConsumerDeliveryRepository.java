package wang.ismy.blb.impl.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wang.ismy.blb.impl.consumer.entity.ConsumerDeliveryDO;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/22 9:11
 */
public interface ConsumerDeliveryRepository extends JpaRepository<ConsumerDeliveryDO,Long> {
    /**
     * 重置该用户的默认收货地址
     * @param userId
     * @return
     */
    @Modifying
    @Query(value="UPDATE ConsumerDeliveryDO SET defaultDelivery = false WHERE userId =:userId")
    int resetDefaultDelivery(@Param("userId") Long userId);

    /**
     * 根据用户ID查询
     * @param userId
     * @return
     */
    @Query("FROM ConsumerDeliveryDO WHERE (removed <> true OR removed is null) AND userId =:userId")
    List<ConsumerDeliveryDO> findAllByUserId(@Param("userId") Long userId);

    /**
     * 根据用户以及是否是默认地址查询
     * @param userId
     * @param defaultDelivery
     * @return
     */
    List<ConsumerDeliveryDO> findAllByUserIdAndDefaultDelivery(Long userId,Boolean defaultDelivery);
}
