package wang.ismy.blb.impl.product.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.blb.api.product.pojo.ProductDO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test void test(){
        List<ProductDO> all = productRepository.findAll();
        assertEquals("黄焖鸡米饭",all.get(0).getProductName());
        assertEquals("黄焖猪脚米饭",all.get(1).getProductName());
        assertEquals("黄焖鸭米饭",all.get(2).getProductName());
    }
}