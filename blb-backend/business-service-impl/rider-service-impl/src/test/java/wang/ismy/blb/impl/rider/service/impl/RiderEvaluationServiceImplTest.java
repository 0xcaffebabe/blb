package wang.ismy.blb.impl.rider.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.auth.UserTypeEnum;
import wang.ismy.blb.api.cache.CacheService;
import wang.ismy.blb.api.consumer.pojo.dto.ConsumerDTO;
import wang.ismy.blb.api.order.enums.OrderStatusEnum;
import wang.ismy.blb.api.order.pojo.dto.OrderResultDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalInfoDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalItemDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalResultDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalSubmitDTO;
import wang.ismy.blb.api.rider.pojo.entity.RiderEvaluationDO;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.rider.client.AuthApiClient;
import wang.ismy.blb.impl.rider.client.ConsumerApiClient;
import wang.ismy.blb.impl.rider.client.OrderApiClient;
import wang.ismy.blb.impl.rider.repository.RiderEvaluationRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class RiderEvaluationServiceImplTest {

    @Autowired
    RiderEvaluationServiceImpl evaluationService;

    @Autowired
    RiderEvaluationRepository evaluationRepository;

    @Test
    @Transactional
    void addEvaluation() {
        String token = "token";
        Long orderId = 1L;
        User user = new User();
        user.setUserId(1L);
        user.setUserType(UserTypeEnum.CONSUMER.getType());
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        evaluationService.setAuthApiClient(authApiClient);

        OrderResultDTO order = new OrderResultDTO();
        order.setOrderId(orderId);
        order.setConsumerId(1L);
        order.setOrderStatus(OrderStatusEnum.DONE.getCode());
        OrderApiClient orderApiClient = mock(OrderApiClient.class);
        when(orderApiClient.getOrder(eq(orderId))).thenReturn(Result.success(order));
        evaluationService.setOrderApiClient(orderApiClient);

        RiderEvalSubmitDTO submitDTO = new RiderEvalSubmitDTO();
        submitDTO.setContent("骑手小哥不错");
        submitDTO.setRanking(new BigDecimal("4.5"));
        submitDTO.setOrderId(orderId);

        var result = evaluationService.addEvaluation(token, submitDTO);
        assertEquals(2L,result.getEvalNumber());

        RiderEvaluationDO evaluationDO = evaluationRepository.findByRiderId(1L).get(1);
        assertEquals(submitDTO.getContent(),evaluationDO.getContent());
        assertEquals(submitDTO.getRanking().setScale(0, RoundingMode.CEILING),
                evaluationDO.getRanking().setScale(0,RoundingMode.CEILING));
    }

    @Test
    void getRiderEvalInfo() {
        Long riderId = 1L;
        CacheService cacheService = mock(CacheService.class);
        evaluationService.setCacheService(cacheService);
        RiderEvalInfoDTO result = evaluationService.getRiderEvalInfo(riderId);
        assertEquals(new BigDecimal("3.5").setScale(2,RoundingMode.CEILING),result.getRanking());
    }

    @Test
    void getRiderEvalList() {
        Long riderId = 1L;
        Pageable pageable = Pageable.of(1L,5L);
        ConsumerApiClient consumerApiClient = mock(ConsumerApiClient.class);
        var consumerMap = Map.of(1L, MockUtils.create(ConsumerDTO.class));
        when(consumerApiClient.getInfo(anyList())).thenReturn(Result.success(consumerMap));
        evaluationService.setConsumerApiClient(consumerApiClient);

        Page<RiderEvalItemDTO> result = evaluationService.getRiderEvalList(riderId, pageable);
        assertEquals(1,result.getTotal());
        assertEquals(consumerMap.get(1L).getUsername(),result.getData().get(0).getConsumer());
        assertEquals("骑手评价1",result.getData().get(0).getContent());
    }
}