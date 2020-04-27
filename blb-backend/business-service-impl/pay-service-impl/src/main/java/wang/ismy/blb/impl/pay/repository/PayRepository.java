package wang.ismy.blb.impl.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.pay.pojo.PayDO;

import java.util.Optional;

/**
 * @author MY
 * @date 2020/4/27 9:01
 */
public interface PayRepository extends JpaRepository<PayDO,Long> {
    /**
     * 根据订单号查询
     * @param orderId
     * @return
     */
    PayDO findByOrderId(Long orderId);
}
