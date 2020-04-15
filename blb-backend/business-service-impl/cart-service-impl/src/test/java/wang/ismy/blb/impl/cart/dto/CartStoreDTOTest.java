package wang.ismy.blb.impl.cart.dto;

import org.junit.jupiter.api.Test;
import wang.ismy.blb.api.cart.CartItem;

import static org.junit.jupiter.api.Assertions.*;

class CartStoreDTOTest {

    @Test
    void testAppend(){
        CartStoreDTO cart = new CartStoreDTO();
        CartItem item = new CartItem();
        item.setProductId(1L);
        cart.append(item);
        assertEquals(1,cart.getCartItemList().size());
    }

    @Test
    void testAppendDuplicate(){
        CartStoreDTO cart = new CartStoreDTO();

        CartItem item1 = new CartItem();
        item1.setProductId(1L);
        item1.setSpecId(1L);
        item1.setProductQuantity(1L);
        cart.append(item1);

        CartItem item2 = new CartItem();
        item2.setProductId(1L);
        item2.setSpecId(1L);
        item2.setProductQuantity(1L);
        cart.append(item2);

        assertEquals(1,cart.getCartItemList().size());
        assertEquals(2L,cart.getCartItemList().get(0).getProductQuantity());

    }
}