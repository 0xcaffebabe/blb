package wang.ismy.blb.impl.rider.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.blb.api.auth.UserTypeEnum;
import wang.ismy.blb.api.rider.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.rider.pojo.dto.RegisterDTO;
import wang.ismy.blb.api.rider.pojo.entity.RiderDO;
import wang.ismy.blb.api.rider.pojo.entity.RiderInfoDO;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.rider.client.AuthApiClient;
import wang.ismy.blb.impl.rider.repository.RiderInfoRepository;
import wang.ismy.blb.impl.rider.repository.RiderRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class RiderServiceImplTest {

    @Autowired
    RiderServiceImpl riderService;

    @Autowired
    RiderRepository riderRepository;

    @Autowired
    RiderInfoRepository riderInfoRepository;

    @Test
    @Transactional
    void register() {
        RegisterDTO dto = MockUtils.create(RegisterDTO.class);
        String result = riderService.register(dto);
        assertEquals( "注册成功，欢迎成为饱了吧第3个骑手",result);

        RiderInfoDO riderInfoDO = riderInfoRepository.findAll().get(2);
        RiderDO riderDO = riderRepository.findAll().get(2);

        assertEquals(dto.getUsername(),riderDO.getUsername());
        assertEquals(dto.getRealName(),riderInfoDO.getRealName());
    }

    @Test
    void login() {
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        riderService.setAuthApiClient(authApiClient);

        String loginStr = "rider-cxk";
        String password = "123";

        when(authApiClient.auth(argThat(user->
                user.getUsername().equals(loginStr) && user.getUserType().equals(UserTypeEnum.RIDER.getType())
                ))).thenReturn(Result.success("token"));

        LoginResultDTO login = riderService.login(loginStr, password);
        assertEquals("token",login.getToken());
        assertEquals("欢迎登录",login.getGreeting());
    }
}