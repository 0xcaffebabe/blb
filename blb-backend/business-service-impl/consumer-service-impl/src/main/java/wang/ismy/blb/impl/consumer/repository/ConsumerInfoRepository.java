package wang.ismy.blb.impl.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.consumer.pojo.ConsumerInfoDO;

/**
 * @author MY
 * @date 2020/4/21 9:51
 */
public interface ConsumerInfoRepository extends JpaRepository<ConsumerInfoDO,Long> { }
