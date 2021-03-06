package wang.ismy.blb.impl.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.consumer.pojo.ConsumerDO;

/**
 * @author MY
 * @date 2020/4/21 9:49
 */
public interface ConsumerRepository extends JpaRepository<ConsumerDO,Long> {
    ConsumerDO findByUsername(String username);
}
