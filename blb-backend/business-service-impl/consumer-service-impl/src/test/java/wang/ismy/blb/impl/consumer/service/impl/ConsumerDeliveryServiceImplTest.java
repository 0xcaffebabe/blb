package wang.ismy.blb.impl.consumer.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.consumer.pojo.dto.DeliveryDTO;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.consumer.client.AuthApiClient;
import wang.ismy.blb.impl.consumer.repository.ConsumerDeliveryRepository;
import wang.ismy.blb.impl.consumer.repository.DeliveryRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class ConsumerDeliveryServiceImplTest {

    @Autowired
    ConsumerDeliveryServiceImpl deliveryService;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    ConsumerDeliveryRepository consumerDeliveryRepository;

    @Test
    @Transactional
    void addDelivery() {
        String token = "token";
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        DeliveryDTO dto = MockUtils.create(DeliveryDTO.class);
        dto.setDefaultDelivery(true);

        User user = new User();
        user.setUserId(1L);

        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));

        deliveryService.setAuthApiClient(authApiClient);
        deliveryService.addDelivery(token,dto);

        assertEquals(4L,deliveryRepository.count());
        var delivery = deliveryRepository.findAll().get(3);
        assertEquals(dto.getBuilding(),delivery.getBuilding());
        assertEquals(dto.getDetail(),delivery.getDetail());

        var consumerDelivery = consumerDeliveryRepository.findAll().get(3);
        assertEquals(dto.getDefaultDelivery(),consumerDelivery.getDefaultDelivery());

        // 验证之前的默认地址是否被重置
        assertEquals(false,consumerDeliveryRepository.findById(2L).get().getDefaultDelivery());
    }

    @Test
    @Transactional
    void updateDelivery() {
        String token = "token";
        Long deliveryId = 1L;
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        DeliveryDTO dto = MockUtils.create(DeliveryDTO.class);
        dto.setDefaultDelivery(true);

        User user = new User();
        user.setUserId(1L);

        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        deliveryService.setAuthApiClient(authApiClient);

        deliveryService.updateDelivery(token,deliveryId,dto);

        assertEquals(3L,deliveryRepository.count());
        var delivery = deliveryRepository.findAll().get(0);
        assertEquals(dto.getBuilding(),delivery.getBuilding());
        assertEquals(dto.getDetail(),delivery.getDetail());

        var consumerDelivery = consumerDeliveryRepository.findAll().get(0);
        assertEquals(dto.getDefaultDelivery(),consumerDelivery.getDefaultDelivery());

        // 验证之前的默认地址是否被重置
        assertEquals(false,consumerDeliveryRepository.findById(2L).get().getDefaultDelivery());
    }

    @Test
    void getDeliveryInfoList() {
        String token = "token";
        Long deliveryId = 1L;
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        DeliveryDTO dto = MockUtils.create(DeliveryDTO.class);
        dto.setDefaultDelivery(true);

        User user = new User();
        user.setUserId(1L);

        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        deliveryService.setAuthApiClient(authApiClient);
        var list = deliveryService.getDeliveryInfoList(token);

        assertEquals(2,list.size());
        assertEquals("翻斗大街42栋",list.get(0).getBuilding());
        assertEquals("爱情公寓A楼",list.get(1).getBuilding());
        assertEquals(true,list.get(1).getDefaultDelivery());
    }

    @Test
    void getDefaultDeliveryInfo() {
        String token = "token";
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        DeliveryDTO dto = MockUtils.create(DeliveryDTO.class);
        dto.setDefaultDelivery(true);

        User user = new User();
        user.setUserId(1L);

        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        deliveryService.setAuthApiClient(authApiClient);
        var delivery = deliveryService.getDefaultDeliveryInfo(token);

        assertEquals("爱情公寓A楼",delivery.getBuilding());
        assertEquals(true,delivery.getDefaultDelivery());
    }

    @Test
    void testGetDefaultDeliveryInfo() {

        var delivery = deliveryService.getDefaultDeliveryInfo(1L);

        assertEquals("爱情公寓A楼",delivery.getBuilding());
        assertEquals(true,delivery.getDefaultDelivery());
    }

    @Test
    void getDeliveryInfo() {
        var delivery = deliveryService.getDeliveryInfo(2L);

        assertEquals("爱情公寓A楼",delivery.getBuilding());
        assertEquals(true,delivery.getDefaultDelivery());
    }
}