package wang.ismy.blb.impl.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.api.product.pojo.ProductDO;

/**
 * @author MY
 * @date 2020/4/17 13:39
 */
public interface ProductRepository extends JpaRepository<ProductDO,Long> { }
