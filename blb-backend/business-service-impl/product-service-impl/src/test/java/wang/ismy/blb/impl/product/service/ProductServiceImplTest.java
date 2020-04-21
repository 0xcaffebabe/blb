package wang.ismy.blb.impl.product.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.blb.api.product.pojo.dto.CartProductGetDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class ProductServiceImplTest {

    @Autowired
    ProductService productService;

    @Test void testGetProduct(){
        ProductDTO product = productService.getProduct(1L);
        assertEquals("黄焖鸡米饭",product.getProductName());
        assertEquals("1号店铺招牌菜",product.getProductCategory().getCategoryName());
        assertEquals(2,product.getProductSpecList().size());
        assertEquals(100,product.getProductSpecList().get(0).getStock());
    }

    @Test void testGetProductList(){
        List<ProductDTO> productList = productService.getProductList(List.of(1L, 2L, 3L));

        var product = productList.get(0);
        assertEquals("黄焖鸡米饭",product.getProductName());
        assertEquals("1号店铺招牌菜",product.getProductCategory().getCategoryName());
        assertEquals(2,product.getProductSpecList().size());
        assertEquals(100,product.getProductSpecList().get(0).getStock());

        product = productList.get(1);
        assertEquals("黄焖猪脚米饭",product.getProductName());
        assertEquals("1号店铺招牌菜",product.getProductCategory().getCategoryName());
        assertEquals(1,product.getProductSpecList().size());
        assertEquals(100,product.getProductSpecList().get(0).getStock());

        product = productList.get(2);
        assertEquals("黄焖鸭米饭",product.getProductName());
        assertEquals("1号店铺招牌菜",product.getProductCategory().getCategoryName());
        assertEquals(1,product.getProductSpecList().size());
        assertEquals(100,product.getProductSpecList().get(0).getStock());
    }

    @Test void testGetProductAndSpecList(){

        var params = List.of(
                CartProductGetDTO.create(1L,1L),
                CartProductGetDTO.create(2L,3L),
                CartProductGetDTO.create(3L,4L),
                CartProductGetDTO.create(1L,2L)
        );

        var productList = productService.getProductAndSpecList(params);

        var product = productList.get(0);
        assertEquals("黄焖鸡米饭",product.getProductName());
        assertEquals("1号店铺招牌菜",product.getProductCategory().getCategoryName());
        assertEquals(1,product.getProductSpecList().size());
        assertEquals(100,product.getProductSpecList().get(0).getStock());

        product = productList.get(1);
        assertEquals("黄焖猪脚米饭",product.getProductName());
        assertEquals("1号店铺招牌菜",product.getProductCategory().getCategoryName());
        assertEquals(1,product.getProductSpecList().size());
        assertEquals(100,product.getProductSpecList().get(0).getStock());

        product = productList.get(2);
        assertEquals("黄焖鸭米饭",product.getProductName());
        assertEquals("1号店铺招牌菜",product.getProductCategory().getCategoryName());
        assertEquals(1,product.getProductSpecList().size());
        assertEquals(100,product.getProductSpecList().get(0).getStock());

        product = productList.get(3);
        assertEquals("黄焖鸡米饭",product.getProductName());
        assertEquals("1号店铺招牌菜",product.getProductCategory().getCategoryName());
        assertEquals(1,product.getProductSpecList().size());
        assertEquals(100,product.getProductSpecList().get(0).getStock());
    }

    @Test void testGetSpec(){
        var productSpec = productService.getProductSpec(1L);
        assertEquals("黄焖鸡-大份",productSpec.getSpecName());

        productSpec = productService.getProductSpec(2L);
        assertEquals("黄焖鸡-小份",productSpec.getSpecName());

        productSpec = productService.getProductSpec(60L);
        assertNull(productSpec);
    }
}