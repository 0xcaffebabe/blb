package wang.ismy.blb.impl.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.consumer.pojo.DeliveryInfoDO;

/**
 * @author MY
 * @date 2020/4/22 9:10
 */
public interface DeliveryRepository extends JpaRepository<DeliveryInfoDO,Long> { }
