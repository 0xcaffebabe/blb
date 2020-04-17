package wang.ismy.blb.api.product.pojo.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductDTOTest {

    @Test void testAppend(){
        ProductDTO dto = new ProductDTO();
        dto.appendSpec(null).appendSpec(null);
        assertEquals(2,dto.getProductSpecList().size());
    }
}