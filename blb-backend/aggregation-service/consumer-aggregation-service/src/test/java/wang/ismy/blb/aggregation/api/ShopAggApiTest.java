package wang.ismy.blb.aggregation.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.aggregation.client.*;
import wang.ismy.blb.aggregation.client.order.OrderApiClient;
import wang.ismy.blb.aggregation.client.order.OrderConsumerApiClient;
import wang.ismy.blb.aggregation.pojo.CartItemShowDTO;
import wang.ismy.blb.aggregation.pojo.CategoryShopDTO;
import wang.ismy.blb.api.cart.CartItem;
import wang.ismy.blb.api.order.pojo.dto.OrderCreateDTO;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderDetailDTO;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderItemDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductSpecDTO;
import wang.ismy.blb.api.product.pojo.dto.ShopProductDTO;
import wang.ismy.blb.api.product.pojo.dto.eval.ConsumerEvalItem;
import wang.ismy.blb.api.product.pojo.dto.eval.ShopEvalInfo;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopItemDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.JsonUtils;
import wang.ismy.blb.common.util.MockUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShopAggApiTest {

    MockMvc mockMvc;
    ShopApiClient shopApiClient;
    ProductCategoryApiClient productCategoryApiClient;
    ProductEvalApiClient productEvalApiClient;
    CartApiClient cartApiClient;
    OrderApiClient orderApiClient;
    OrderConsumerApiClient orderConsumerApiClient;
    ProductApiClient productApiClient;
    @BeforeEach
    void setup(){
        shopApiClient = mock(ShopApiClient.class);
        productCategoryApiClient = mock(ProductCategoryApiClient.class);
        productEvalApiClient = mock(ProductEvalApiClient.class);
        cartApiClient = mock(CartApiClient.class);
        orderApiClient = mock(OrderApiClient.class);
        orderConsumerApiClient = mock(OrderConsumerApiClient.class);
        productApiClient = mock(ProductApiClient.class);
        mockMvc = MockMvcBuilders.standaloneSetup(
                new ShopAggApi(shopApiClient,
                        productCategoryApiClient,
                        productEvalApiClient,
                        cartApiClient,
                        orderApiClient,
                        orderConsumerApiClient,
                        productApiClient
                        )
        ).build();
    }

    @Test
    void getNearbyShop() throws Exception {
        String location = "117,29";

        ShopItemDTO shopItem1 = new ShopItemDTO();
        shopItem1.setShopId(1L);
        shopItem1.setShopName("1");
        ShopItemDTO shopItem2 = new ShopItemDTO();
        shopItem2.setShopId(2L);
        shopItem2.setShopName("2");
        when(shopApiClient.getNearbyShop(eq(location),eq(1L),eq(10L))).thenReturn(Result.success(new Page<>(2L, List.of(shopItem1,shopItem2))));

        ShopInfoDTO shopInfo1 = new ShopInfoDTO();
        shopInfo1.setStartingPrice(new BigDecimal("10"));
        shopInfo1.setDeliveryFee(new BigDecimal("2"));
        ShopInfoDTO shopInfo2 = new ShopInfoDTO();
        shopInfo2.setStartingPrice(new BigDecimal("10"));
        shopInfo2.setDeliveryFee(new BigDecimal("2"));
        when(shopApiClient.getShopInfo(anyList())).thenReturn(Result.success(
                Map.of(1L,shopInfo1,2L,shopInfo2)
        ));

        CategoryShopDTO shop1 = new CategoryShopDTO();
        shop1.setShopId(1L);
        shop1.setShopName("1");
        shop1.setDeliveryTime("38分钟");
        shop1.setStartingPrice(new BigDecimal("10"));
        shop1.setDeliveryFee(new BigDecimal("2"));
        shop1.setSales(25);
        CategoryShopDTO shop2 = new CategoryShopDTO();
        shop2.setShopId(2L);
        shop2.setShopName("2");
        shop2.setDeliveryTime("38分钟");
        shop2.setStartingPrice(new BigDecimal("10"));
        shop2.setDeliveryFee(new BigDecimal("2"));
        shop2.setSales(25);
        mockMvc.perform(get("/shop/vicinity")
                .param("location",location)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(new Page(2L,List.of(shop1,shop2))))));

    }

    @Test
    void getShopInfo() throws Exception {
        Long shopId = 1L;
        ShopInfoDTO shopInfoDTO = MockUtils.create(ShopInfoDTO.class);
        when(shopApiClient.getShopInfo(eq(shopId))).thenReturn(Result.success(shopInfoDTO));

        mockMvc.perform(get("/shop/info/"+shopId))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(shopInfoDTO))));
    }

    @Test
    void getCategoryList() throws Exception {
        Long shopId = 1L;
        var list = MockUtils.create(ProductCategoryDTO.class,5);
        when(productCategoryApiClient.getCategoryList(eq(shopId))).thenReturn(Result.success(list));

        mockMvc.perform(get("/shop/"+shopId+"/category"))
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(list))));
    }

    @Test
    void getProductByCategory() throws Exception {
        Long shopId =1L;
        Long categoryId = 1L;
        var list = MockUtils.create(ShopProductDTO.class,5);
        when(productCategoryApiClient.getProductByCategory(eq(categoryId))).thenReturn(Result.success(list));
        mockMvc.perform(get("/shop/"+shopId+"/"+categoryId+"/product"))
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(list))));

    }

    @Test
    void getShopEvalInfo() throws Exception {
        Long shopId = 1L;
        ShopEvalInfo shopEvalInfo = MockUtils.create(ShopEvalInfo.class);
        when(productEvalApiClient.getShopEvalInfo(eq(shopId)))
                .thenReturn(Result.success(shopEvalInfo));

        mockMvc.perform(get("/shop/"+shopId+"/evaluation"))
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(shopEvalInfo))));
    }

    @Test
    void getShopEvalList() throws Exception {
        Long shopId = 1L;
        var list = MockUtils.create(ConsumerEvalItem.class,10);
        Page<ConsumerEvalItem> page = new Page<>(10L, list);
        when(productEvalApiClient.getShopEvalList(eq(shopId),eq(1L),eq(10L)))
                .thenReturn(Result.success(page));
        mockMvc.perform(get("/shop/"+shopId+"/evaluation/list"))
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtils.parse(Result.success(page))));
    }

    @Test
    void getCartList() throws Exception {
        Long shopId = 1L;
        String token = "token";
//        CartItem item1 = new CartItem();
//        item1.setSpecId(1L);
//        item1.setProductId(1L);
//        CartItem item2 = new CartItem();
//        item2.setSpecId(2L);
//        item2.setProductId(2L);
//        when(cartApiClient.getCartList(eq(token),eq(shopId))).thenReturn(Result.success(List.of(item1,item2)));
//        ProductDTO productDTO1 = new ProductDTO();
//        ProductDTO productDTO2 = new ProductDTO();
//        ProductSpecDTO specDTO1 = new ProductSpecDTO();
//        specDTO1.setSpecId(1L);
//        specDTO1.setSpecName("规格1");
//        ProductSpecDTO specDTO2 = new ProductSpecDTO();
//        specDTO2.setSpecId(2L);
//        specDTO2.setSpecName("规格2");
//        productDTO1.setProductSpecList(List.of(specDTO1));
//        productDTO2.setProductSpecList(List.of(specDTO2));
//        when(productApiClient.getListByProductAndSpecList(argThat(list->
//                list.size() == 2
//                && list.get(0).getProductId().equals(1L)
//                && list.get(0).getSpecId().equals(1L)
//                && list.get(1).getProductId().equals(2L)
//                && list.get(1).getSpecId().equals(2L)
//        ))).thenReturn(Result.success(List.of(productDTO1,productDTO2)));
//        CartItemShowDTO itemShowDTO1 = new CartItemShowDTO();
//        CartItemShowDTO itemShowDTO2 = new CartItemShowDTO();
//        itemShowDTO1.setProductId(1L);
//        itemShowDTO1.setSpecId(1L);
//        itemShowDTO1.setSpecName("规格1");
//
//        itemShowDTO2.setProductId(2L);
//        itemShowDTO2.setSpecId(2L);
//        itemShowDTO2.setSpecName("规格2");
        var list = MockUtils.create(CartItem.class,10);
        when(cartApiClient.getCartList(eq(token),eq(shopId))).thenReturn(Result.success(list));
        mockMvc.perform(get("/shop/"+shopId+"/cart")
            .header(SystemConstant.TOKEN,token)
        )
                .andExpect(content().json(JsonUtils.parse(Result.success(list))));
    }

    @Test
    void addProduct() throws Exception {
        Long shopId = 1L;
        Long productId = 1L;
        Long specId = 1L;
        Long quantity = 1L;
        String token = "token";
        when(cartApiClient.addProduct(eq(token),eq(productId),eq(specId),eq(quantity)))
                .thenReturn(Result.success());
        mockMvc.perform(put("/shop/"+shopId+"/cart/"+productId+"/"+specId)
                .param("quantity", quantity.toString())
            .header(SystemConstant.TOKEN,token)
        )
                .andExpect(content().json(JsonUtils.parse(Result.success())));
    }

    @Test
    void deleteProduct() throws Exception {
        Long shopId = 1L;
        Long productId = 1L;
        Long specId = 1L;
        String token = "token";
        when(cartApiClient.deleteProduct(eq(token),eq(productId),eq(specId)))
                .thenReturn(Result.success());
        mockMvc.perform(delete("/shop/"+shopId+"/cart/"+productId+"/"+specId)
                .header(SystemConstant.TOKEN,token)
        )
                .andExpect(content().json(JsonUtils.parse(Result.success())));
    }

    @Test
    void deleteCartProductList () throws Exception {
        Long shopId = 1L;
        when(cartApiClient.delAllProductList(eq(shopId)))
                .thenReturn(Result.success());
        mockMvc.perform(delete("/shop/"+shopId+"/cart/")
        )
                .andExpect(content().json(JsonUtils.parse(Result.success())));
    }


    @Test
    void submitOrder() throws Exception {
        OrderCreateDTO orderCreateDTO = MockUtils.create(OrderCreateDTO.class);
        String orderId = "1";
        when(orderApiClient.addOrder(eq(orderCreateDTO))).thenReturn(Result.success(orderId));

        mockMvc.perform(post("/shop/order")
            .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.parse(orderCreateDTO))
        )
                .andExpect(content().json(JsonUtils.parse(Result.success(orderId))));
    }

    @Test
    void getConsumerOrderList() throws Exception {
        var list = MockUtils.create(ConsumerOrderItemDTO.class,10);
        var page = new Page<>(15L,list);
        when(orderConsumerApiClient.getConsumerOrderList(any(),any())).thenReturn(Result.success(page));

        mockMvc.perform(get("/shop/order"))
                .andExpect(content().json(JsonUtils.parse(Result.success(page))));
    }

    @Test
    void getConsumerOrderDetail() throws Exception {
        Long orderId = 1L;
        ConsumerOrderDetailDTO detail = MockUtils.create(ConsumerOrderDetailDTO.class);
        when(orderConsumerApiClient.getConsumerOrderDetail(eq(orderId))).thenReturn(Result.success(detail));

        mockMvc.perform(get("/shop/order/"+orderId))
                .andExpect(content().json(JsonUtils.parse(Result.success(detail))));
    }
}