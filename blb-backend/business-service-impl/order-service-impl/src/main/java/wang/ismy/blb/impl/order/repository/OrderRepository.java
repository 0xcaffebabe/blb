package wang.ismy.blb.impl.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.order.pojo.entity.OrderDO;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/24 9:26
 */
public interface OrderRepository extends JpaRepository<OrderDO,Long> {

    /**
     * 根据店铺ID拉取订单
     * @param shopId
     * @param pageable
     * @return
     */
    Page<OrderDO> findAllByShopId(Long shopId, Pageable pageable);

    /**
     * 根据消费者ID查询
     * @param consumerId
     * @param pageable
     * @return
     */
    Page<OrderDO> findAllByConsumerIdOrderByCreateTimeDesc(Long consumerId, Pageable pageable);
}
