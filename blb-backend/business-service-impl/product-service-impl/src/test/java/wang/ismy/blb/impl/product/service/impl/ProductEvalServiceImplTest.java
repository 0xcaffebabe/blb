package wang.ismy.blb.impl.product.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.auth.UserTypeEnum;
import wang.ismy.blb.api.cache.CacheService;
import wang.ismy.blb.api.consumer.ConsumerApi;
import wang.ismy.blb.api.consumer.pojo.dto.ConsumerDTO;
import wang.ismy.blb.api.order.enums.OrderStatusEnum;
import wang.ismy.blb.api.order.pojo.dto.OrderDetailItemDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderResultDTO;
import wang.ismy.blb.api.product.pojo.dto.eval.EvalCreateDTO;
import wang.ismy.blb.api.product.pojo.dto.eval.ShopEvalInfo;
import wang.ismy.blb.common.SnowFlake;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.product.ProductConstant;
import wang.ismy.blb.impl.product.client.AuthApiClient;
import wang.ismy.blb.impl.product.client.ConsumerApiClient;
import wang.ismy.blb.impl.product.client.OrderApiClient;
import wang.ismy.blb.impl.product.repository.ProductEvaluationRepository;
import wang.ismy.blb.impl.product.repository.ProductRepository;
import wang.ismy.blb.impl.product.service.ElasticsearchService;
import wang.ismy.blb.impl.product.service.ProductEvalService;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class ProductEvalServiceImplTest {

    @Autowired
    ProductEvaluationRepository evaluationRepository;

    @Autowired
    ProductRepository productRepository;

    @Test void testGetShopEvalHitCache(){
        Long shopId = 1L;
        CacheService cacheService = mock(CacheService.class);
        ProductEvalServiceImpl productEvalService =
                new ProductEvalServiceImpl(evaluationRepository, cacheService,null,null,null,null,null,null);
        when(cacheService.get(eq("shop-eval-"+shopId),eq(BigDecimal.class))).thenReturn(new BigDecimal("3.5"));
        BigDecimal shopEval = productEvalService.getShopEval(shopId);
        assertEquals(new BigDecimal("3.5"),shopEval);
    }

    @Test void testGetShopEvalNotHitCache(){
        Long shopId = 1L;
        CacheService cacheService = mock(CacheService.class);
        ProductEvalServiceImpl productEvalService = new ProductEvalServiceImpl(evaluationRepository, cacheService,null,null,null,null,null,null);
        when(cacheService.get(eq("shop-eval-"+shopId),eq(BigDecimal.class))).thenReturn(null);
        BigDecimal shopEval = productEvalService.getShopEval(shopId);

        verify(cacheService).put(eq("shop-eval-"+ shopId),eq(new BigDecimal("3.5")),eq(ProductConstant.CACHE_TTL));

        assertEquals(new BigDecimal("3.5"),shopEval);
    }

    @Test void testGetShopEvalInfoHitCache(){
        Long shopId = 1L;
        CacheService cacheService = mock(CacheService.class);
        ShopEvalInfo info = new ShopEvalInfo();
        info.setWordCloud(List.of("好吃","垃圾"));
        info.setRanking(new BigDecimal("3.5"));

        ElasticsearchService elasticsearchService = mock(ElasticsearchService.class);
        ProductEvalServiceImpl productEvalService = new ProductEvalServiceImpl(evaluationRepository, cacheService,elasticsearchService,null,null,null,null,null);
        when(cacheService.get(eq("shop-eval-info-"+shopId),eq(ShopEvalInfo.class))).thenReturn(info);

        assertEquals(info,productEvalService.getShopEvalInfo(shopId));

    }

    @Test void testGetShopEvalInfoNotHitCache(){
        Long shopId = 1L;
        CacheService cacheService = mock(CacheService.class);

        ElasticsearchService elasticsearchService = mock(ElasticsearchService.class);
        ProductEvalServiceImpl productEvalService = new ProductEvalServiceImpl(evaluationRepository, cacheService,elasticsearchService,null,null,null,null,null);
        when(elasticsearchService.getWordCloud(anyString())).thenReturn(List.of("满意","难吃"));
        var info = productEvalService.getShopEvalInfo(shopId);
        assertEquals(new BigDecimal("3.5"),info.getRanking());
        assertEquals(List.of("满意","难吃"),info.getWordCloud());

    }

    @Test void testGetShopEvalList(){
        Long shopId = 1L;
        Pageable pageable = Pageable.of(1L, 3L);
        ConsumerApiClient mockConsumer = mock(ConsumerApiClient.class);
        when(mockConsumer.getInfo(argThat((List list)->list.size() == 3))).thenReturn(Result.success(
                Map.of(1L,MockUtils.create(ConsumerDTO.class),2L,MockUtils.create(ConsumerDTO.class),3L,MockUtils.create(ConsumerDTO.class))
        ));

        ProductEvalServiceImpl productEvalService = new ProductEvalServiceImpl(evaluationRepository, null,null,mockConsumer,null,null,null,null);
        var page = productEvalService.getShopEvalList(shopId,pageable);
        assertEquals(3,page.getData().size());
        for (int i = 0; i < 3; i++) {
            assertTrue(page.getData().get(i).getNickName().startsWith("username"));
            assertTrue(page.getData().get(i).getContent().startsWith("黄焖鸡评价"));
        }
    }

    @Test void testCreateEval(){
        String token = "token";
        Long orderId = 1L;
        Long userId = 1L;

        User user = new User();
        user.setUserId(1L);
        user.setUserType(UserTypeEnum.CONSUMER.getType());

        OrderResultDTO order = new OrderResultDTO();
        order.setOrderId(1L);
        order.setShopId(1L);
        order.setConsumerId(userId);
        order.setOrderStatus(OrderStatusEnum.DONE.getCode());
        OrderDetailItemDTO orderDetail = new OrderDetailItemDTO();
        orderDetail.setProductId(1L);
        order.setOrderDetailList(List.of(orderDetail));

        EvalCreateDTO dto = new EvalCreateDTO();
        dto.setOrderId(orderId);

        AuthApiClient authApiClient = mock(AuthApiClient.class);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        OrderApiClient orderApiClient = mock(OrderApiClient.class);
        when(orderApiClient.getOrder(eq(orderId))).thenReturn(Result.success(order));

        ProductEvaluationRepository evaluationRepository = mock(ProductEvaluationRepository.class);
        SnowFlake snowFlake = mock(SnowFlake.class);
        ProductEvalService productEvalService = new ProductEvalServiceImpl(evaluationRepository,null,null,null,orderApiClient,authApiClient,
                productRepository,snowFlake);
        productEvalService.createEval(token,dto);
        verify(evaluationRepository,times(1)).save(any());
    }
}