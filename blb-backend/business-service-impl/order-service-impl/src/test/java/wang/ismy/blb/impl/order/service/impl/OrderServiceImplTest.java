package wang.ismy.blb.impl.order.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.cache.CacheService;
import wang.ismy.blb.api.consumer.ConsumerDeliveryApi;
import wang.ismy.blb.api.consumer.pojo.dto.ConsumerDTO;
import wang.ismy.blb.api.consumer.pojo.dto.DeliveryDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderCreateDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderDetailCreateDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderResultDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderValidCode;
import wang.ismy.blb.api.order.pojo.entity.OrderDO;
import wang.ismy.blb.api.product.pojo.dto.ProductCategoryDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductDTO;
import wang.ismy.blb.api.product.pojo.dto.ProductSpecDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.order.OrderConstant;
import wang.ismy.blb.impl.order.client.*;
import wang.ismy.blb.impl.order.repository.OrderDetailRepository;
import wang.ismy.blb.impl.order.repository.OrderRepository;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class OrderServiceImplTest {

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository detailRepository;

    @Test
    void getOrder() {
        OrderResultDTO order = orderService.getOrder(1L);
        assertEquals("蔡徐坤",order.getConsumerName());
        assertEquals(2,order.getOrderDetailList().size());
        assertEquals("黄焖鸡米饭",order.getOrderDetailList().get(0).getProductName());
        assertEquals(1L,order.getOrderDetailList().get(0).getProductSpec());
        assertEquals("黄焖鸡米饭",order.getOrderDetailList().get(1).getProductName());
        assertEquals(2L,order.getOrderDetailList().get(1).getProductSpec());
    }

    @Test
    @Transactional
    void addOrder() {
        String token = "token";
        User user = new User();
        user.setUserId(1L);
        AuthApiClient authApiClient  = mock(AuthApiClient.class);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        orderService.setAuthApiClient(authApiClient);

        ProductCategoryDTO category = MockUtils.create(ProductCategoryDTO.class);
        category.setShopId(1L);
        ProductDTO product1 = MockUtils.create(ProductDTO.class);
        product1.setProductId(1L);
        product1.setProductName("黄焖鸡米饭");
        product1.setProductDesc("黄焖鸡米饭描述");
        product1.setProductImg("黄焖鸡米饭图片");
        ProductDTO product2 = MockUtils.create(ProductDTO.class);
        product2.setProductId(1L);
        product2.setProductName("黄焖鸡米饭");
        product2.setProductDesc("黄焖鸡米饭描述");
        product2.setProductImg("黄焖鸡米饭图片");
        product1.setProductCategory(category);
        ProductSpecDTO spec1 = MockUtils.create(ProductSpecDTO.class);
        spec1.setSpecId(1L);
        spec1.setPrice(new BigDecimal("3.5"));
        spec1.setPackageFee(new BigDecimal("1"));
        ProductSpecDTO spec2 = MockUtils.create(ProductSpecDTO.class);
        spec2.setSpecId(2L);
        spec2.setPrice(new BigDecimal("3.5"));
        spec2.setPackageFee(new BigDecimal("1"));
        product1.appendSpec(spec1);
        product2.appendSpec(spec2);
        ProductApiClient productApiClient = mock(ProductApiClient.class);
        when(productApiClient.getListByProductAndSpecList(any())).thenReturn(Result.success(List.of(product1,product2)));
        orderService.setProductApiClient(productApiClient);

        ConsumerApiClient consumerApiClient = mock(ConsumerApiClient.class);
        ConsumerDTO consumerDTO = new ConsumerDTO();
        consumerDTO.setPhone("17359561234");
        consumerDTO.setRealName("蔡徐坤");
        when(consumerApiClient.getInfo(eq(1L))).thenReturn(Result.success(consumerDTO));
        orderService.setConsumerApiClient(consumerApiClient);

        DeliveryDTO deliveryDTO = new DeliveryDTO();
        deliveryDTO.setDeliveryId(1L);
        deliveryDTO.setBuilding("泉州师范学院软件学院");
        deliveryDTO.setDetail("男B305");
        ConsumerDeliveryApiClient consumerDeliveryApiClient = mock(ConsumerDeliveryApiClient.class);
        when(consumerDeliveryApiClient.getDeliveryInfo(eq(1L))).thenReturn(Result.success(deliveryDTO));
        orderService.setDeliveryApiClient(consumerDeliveryApiClient);

        CacheService cacheService = mock(CacheService.class);

        OrderCreateDTO orderCreateDTO = new OrderCreateDTO();
        orderCreateDTO.setDeliveryId(1L);
        orderCreateDTO.setOrderNote("辣子鸡不要辣");
        OrderDetailCreateDTO detail1 = new OrderDetailCreateDTO();
        detail1.setProductId(1L);
        detail1.setQuantity(2);
        detail1.setSpecId(1L);
        OrderDetailCreateDTO detail2 = new OrderDetailCreateDTO();
        detail2.setProductId(1L);
        detail2.setQuantity(2);
        detail2.setSpecId(2L);
        orderCreateDTO.setProductList(List.of(detail1,detail2));
        orderService.setCacheService(cacheService);
        String orderId = orderService.addOrder(token, orderCreateDTO);

        assertNotNull(orderId);
        var order = orderRepository.findById(Long.parseLong(orderId)).orElseThrow();
        var detailList = detailRepository.findAllByOrderId(Long.parseLong(orderId));

        assertEquals(new BigDecimal("18.0"),order.getOrderAmount());
        assertEquals("蔡徐坤",order.getConsumerName());

        assertEquals(2,detailList.size());
        assertEquals(new BigDecimal("9.0"),detailList.get(0).getProductPrice());
        assertEquals("黄焖鸡米饭",detailList.get(0).getProductName());
        assertEquals(1L,detailList.get(0).getProductSpec());

        assertEquals(new BigDecimal("9.0"),detailList.get(1).getProductPrice());
        assertEquals("黄焖鸡米饭",detailList.get(1).getProductName());
        assertEquals(2L,detailList.get(1).getProductSpec());

        // 验证订单验证码
        verify(cacheService).put(eq("order-valid-code-"+orderId),argThat((OrderValidCode code)->{
            return code.getDiningOutCode().length() == 4 && code.getTakeMealCode().length() == 4;
        }),eq(OrderConstant.VALID_CODE_TTL));
    }

    @Test
    @Transactional
    void updateOrderStatus() {
        Long orderId = 1L;
        Integer status = 2;

        orderService.updateOrderStatus(orderId,status);

        OrderDO orderDO = orderRepository.findById(orderId).orElseThrow();
        assertEquals(2, orderDO.getOrderStatus());
    }

    @Test
    @Transactional
    void updatePayStatus() {
        Long orderId = 1L;
        Integer status = 2;

        orderService.updatePayStatus(orderId,status);

        OrderDO orderDO = orderRepository.findById(orderId).orElseThrow();
        assertEquals(2, orderDO.getPayStatus());
    }

    @Test
    void getOrderValidCode() {
        Long orderId = 1L;
        OrderValidCode validCode = mock(OrderValidCode.class);
        CacheService cacheService = mock(CacheService.class);
        when(cacheService.get(eq("order-valid-code-"+orderId),eq(OrderValidCode.class)))
                .thenReturn(validCode);
        orderService.setCacheService(cacheService);
        var ret = orderService.getOrderValidCode(orderId);
        assertEquals(validCode,ret);

    }

    @Test
    @Transactional
    void updateOrderAmount() {
        String token = "token";
        Long orderId = 1L;
        BigDecimal amount = new BigDecimal("10");
        User user = new User();
        user.setUserId(1L);
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        orderService.setAuthApiClient(authApiClient);

        ShopInfoDTO shopInfo = new ShopInfoDTO();
        shopInfo.setSellerId(1L);
        ShopApiClient shopApiClient = mock(ShopApiClient.class);
        when(shopApiClient.getShopInfo(eq(1L))).thenReturn(Result.success(shopInfo));
        orderService.setShopApiClient(shopApiClient);

        orderService.updateOrderAmount(token,orderId,amount);
        OrderDO orderDO = orderRepository.findById(orderId).orElseThrow();
        assertEquals(amount, orderDO.getOrderAmount());
    }

    @Test
    void getProductSales() {
        var list = List.of(1L,2L,3L);
        var map = orderService.getProductSales(list);
        for (long i = 1; i <= 3; i++) {
            assertEquals(2L,map.get(i));
        }
    }
}