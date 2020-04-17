package wang.ismy.blb.impl.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.blb.impl.product.pojo.ProductStockDO;

/**
 * @author MY
 * @date 2020/4/17 14:15
 */
public interface ProductStockRepository extends JpaRepository<ProductStockDO,Long> { }
