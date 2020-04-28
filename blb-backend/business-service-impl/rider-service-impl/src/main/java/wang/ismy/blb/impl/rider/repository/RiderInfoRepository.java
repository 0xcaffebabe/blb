package wang.ismy.blb.impl.rider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.rider.pojo.entity.RiderInfoDO;

/**
 * @author MY
 * @date 2020/4/28 8:56
 */
public interface RiderInfoRepository extends JpaRepository<RiderInfoDO,Long> { }
