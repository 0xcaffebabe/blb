package wang.ismy.blb.impl.rider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.rider.pojo.entity.RiderDO;

/**
 * @author MY
 * @date 2020/4/28 8:54
 */
public interface RiderRepository extends JpaRepository<RiderDO,Long> {
    /**
     * 根据用户名查询存在
     * @param username
     * @return
     */
    boolean existsByUsername(String username);

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    RiderDO findByUsername(String username);
}
