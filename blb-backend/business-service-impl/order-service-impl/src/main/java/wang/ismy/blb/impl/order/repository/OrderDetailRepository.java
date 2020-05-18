package wang.ismy.blb.impl.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.order.pojo.entity.OrderDetailDO;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/24 9:26
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetailDO,Long> {
    /**
     * 根据订单ID查询订单详情
     * @param orderId
     * @return
     */
    List<OrderDetailDO> findAllByOrderId(Long orderId);

    /**
     * 根据订单ID列表批量查询
     * @param orderIdList
     * @return
     */
    List<OrderDetailDO> findAllByOrderIdIn(List<Long> orderIdList);

    /**
     * 根据商品ID批量查询
     * @param productId
     * @return
     */
    List<OrderDetailDO> findAllByProductIdIn(List<Long> productId);
}
