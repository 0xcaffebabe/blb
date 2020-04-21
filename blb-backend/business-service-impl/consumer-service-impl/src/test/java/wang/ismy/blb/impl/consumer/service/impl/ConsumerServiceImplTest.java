package wang.ismy.blb.impl.consumer.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.blb.api.auth.AuthApi;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.auth.UserTypeEnum;
import wang.ismy.blb.api.consumer.pojo.ConsumerDO;
import wang.ismy.blb.api.consumer.pojo.ConsumerInfoDO;
import wang.ismy.blb.api.consumer.pojo.dto.ConsumerUpdateDTO;
import wang.ismy.blb.api.consumer.pojo.dto.RegisterDTO;
import wang.ismy.blb.api.consumer.pojo.dto.RegisterResultDTO;
import wang.ismy.blb.common.SnowFlake;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.consumer.client.AuthApiClient;
import wang.ismy.blb.impl.consumer.repository.ConsumerInfoRepository;
import wang.ismy.blb.impl.consumer.repository.ConsumerRepository;
import wang.ismy.blb.impl.consumer.service.ConsumerService;

import javax.sound.midi.Soundbank;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class ConsumerServiceImplTest {

    @Autowired
    ConsumerServiceImpl consumerService;

    @Autowired
    ConsumerRepository consumerRepository;

    @Autowired
    ConsumerInfoRepository infoRepository;

    @Test
    @Transactional
    void testRegister() {
        RegisterDTO registerDTO = MockUtils.create(RegisterDTO.class);
        RegisterResultDTO result = consumerService.register(registerDTO);
        assertEquals(3L,result.getUserNumber());

        ConsumerDO consumer = consumerRepository.findByUsername(registerDTO.getUsername());
        assertEquals(DigestUtils.md5Hex(registerDTO.getPassword()),consumer.getPassword());
        System.out.println(consumer.getPassword());
        ConsumerInfoDO consumerInfo = infoRepository.findById(consumer.getUserId()).orElse(null);

        assertEquals(registerDTO.getRealName(),consumerInfo.getRealName());
    }

    @Transactional
    @Test void testLogin (){
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        String str = "cxk";
        String password = "123";
        when(authApiClient.auth(any())).thenReturn(Result.success("token"));
        consumerService.setAuthApiClient(authApiClient);
        var result = consumerService.login(str,password);
        assertEquals("token",result.getToken());
    }

    @Transactional
    @Test void testGetSelfInfo (){
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        String token = "token";
        User user = new User();
        user.setUserType(UserTypeEnum.CONSUMER.getType());
        user.setUsername("cxk");
        user.setUserId(1L);

        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));

        consumerService.setAuthApiClient(authApiClient);
        var result = consumerService.getInfo(token);
        assertEquals("cxk",result.getUsername());
        assertEquals("蔡徐坤",result.getRealName());
    }

    @Transactional
    @Test void testGetInfoById (){
        Long consumerId= 1L;

        var result = consumerService.getInfo(consumerId);
        assertEquals("cxk",result.getUsername());
        assertEquals("蔡徐坤",result.getRealName());
    }

    @Transactional
    @Test void testGetInfoBatch(){
        var list = List.of(1L,2L);

        var map = consumerService.getInfo(list);
        assertEquals(2,map.size());
        assertEquals("cxk",map.get(1L).getUsername());
        assertEquals("蔡徐坤",map.get(1L).getRealName());

        assertEquals("xxl",map.get(2L).getUsername());
        assertEquals("徐雪莉",map.get(2L).getRealName());
    }

    @Transactional
    @Test void testUpdateInfo() {
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        String token = "token";
        User user = new User();
        user.setUserType(UserTypeEnum.CONSUMER.getType());
        user.setUsername("cxk");
        user.setUserId(1L);

        ConsumerUpdateDTO dto = new ConsumerUpdateDTO();
        dto.setUsername("cxk1");
        dto.setAvatar("new avatar");
        dto.setPhone("173595634321");
        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        consumerService.setAuthApiClient(authApiClient);

        consumerService.updateInfo(token,dto);
        assertEquals(dto.getUsername(),consumerRepository.findById(1L).get().getUsername());
        assertEquals(dto.getAvatar(),infoRepository.findById(1L).get().getAvatar());
    }

    @Transactional
    @Test void testUpdatePassword() {
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        String token = "token";
        User user = new User();
        user.setUserType(UserTypeEnum.CONSUMER.getType());
        user.setUsername("cxk");
        user.setUserId(1L);

        when(authApiClient.valid(eq(token))).thenReturn(Result.success(user));
        consumerService.setAuthApiClient(authApiClient);

        consumerService.updatePassword(token,"123","456");
        assertEquals(DigestUtils.md5Hex("456"),consumerRepository.findById(1L).get().getPassword());
    }
}