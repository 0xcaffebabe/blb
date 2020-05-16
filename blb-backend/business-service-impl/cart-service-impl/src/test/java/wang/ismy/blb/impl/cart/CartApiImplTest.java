package wang.ismy.blb.impl.cart;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.api.cart.CartItem;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.JsonUtils;
import wang.ismy.blb.impl.cart.service.CartService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CartApiImplTest {

    private MockMvc mockMvc;
    private CartService cartService;

    @BeforeEach
    public void setUp() throws Exception {
        cartService = mock(CartService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new CartApiImpl(cartService)).build();
    }

    @Test
    void testAddProduct() throws Exception {
        String token = "token";
        Long productId = 1L;
        Long specId = 2L;
        Long quantity = 3L;
        mockMvc.perform(post("/v1/api/product/"+productId+"/"+specId).contentType(MediaType.APPLICATION_JSON_VALUE).header("TOKEN",token).param("quantity",quantity+""))
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(Result.success())));
        verify(cartService).addProduct(eq(token),eq(productId),eq(specId),eq(quantity));
    }

    @Test
    void testGetProductList() throws Exception {
        String token = "token";
        Long shopId = 1L;
        List<CartItem> cartItems = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            CartItem item = new CartItem();
            item.setProductId((long) i);
            cartItems.add(item);
        }
        when(cartService.getCartList(eq(token),eq(shopId))).thenReturn(cartItems);
        mockMvc.perform(get("/v1/api/list/"+shopId).contentType(MediaType.APPLICATION_JSON_VALUE).header("TOKEN",token))
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(cartItems))));
    }

    @Test
    void testDeleteProduct() throws Exception {
        String token = "token";
        Long productId = 1L;
        Long specId = 2L;

        mockMvc.perform(delete("/v1/api/product/"+productId+"/"+specId).contentType(MediaType.APPLICATION_JSON_VALUE).header("TOKEN",token))
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(Result.success())));
        verify(cartService).deleteProduct(eq(token),eq(productId),eq(specId));
    }

    @Test
    void testDeleteProductList () throws Exception {
        String token = "token";
        Long shopId = 1L;
        mockMvc.perform(delete("/v1/api/list/"+shopId).contentType(MediaType.APPLICATION_JSON_VALUE).header("TOKEN",token))
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(Result.success())));
        verify(cartService).deleteProductList(eq(token),eq(shopId));
    }
}