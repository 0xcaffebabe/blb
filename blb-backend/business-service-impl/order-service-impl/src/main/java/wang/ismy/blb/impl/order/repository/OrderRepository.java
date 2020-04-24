package wang.ismy.blb.impl.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.order.pojo.entity.OrderDO;

/**
 * @author MY
 * @date 2020/4/24 9:26
 */
public interface OrderRepository extends JpaRepository<OrderDO,Long> {
}
