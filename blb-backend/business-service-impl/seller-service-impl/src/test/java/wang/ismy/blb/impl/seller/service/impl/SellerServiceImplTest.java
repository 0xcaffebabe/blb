package wang.ismy.blb.impl.seller.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.blb.api.seller.pojo.SellerInfoDTO;
import wang.ismy.blb.api.seller.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.seller.pojo.dto.SellerCreateDTO;
import wang.ismy.blb.api.seller.pojo.dto.SellerRegisterResultDTO;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.seller.client.AuthApiClient;
import wang.ismy.blb.impl.seller.repository.SellerInfoRepository;
import wang.ismy.blb.impl.seller.repository.SellerRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class SellerServiceImplTest {

    @Autowired
    SellerServiceImpl sellerService;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    SellerInfoRepository sellerInfoRepository;

    @Test
    void getSellerInfo() {
        SellerInfoDTO dto = sellerService.getSellerInfo(1L);
        assertEquals("蔡徐坤",dto.getRealName());
    }

    @Test
    @Transactional
    void register() {
        SellerCreateDTO createDTO = MockUtils.create(SellerCreateDTO.class);
        var result = sellerService.register(createDTO);
        assertEquals(3L,result.getSellerNumber());
        var seller = sellerRepository.findAll().get(2);
        var info = sellerInfoRepository.findAll().get(2);

        assertEquals(createDTO.getUsername(),seller.getUsername());
        assertEquals(DigestUtils.md5Hex(createDTO.getPassword()),seller.getPassword());
        assertNotNull(info);
    }

    @Test
    void login() {
        AuthApiClient authApiClient = mock(AuthApiClient.class);
        when(authApiClient.auth(any())).thenReturn(Result.success("token"));
        sellerService.setAuthApiClient(authApiClient);

        LoginResultDTO login = sellerService.login("seller-cxk", "123");
        assertEquals(0,login.getStatus());
        assertEquals("token",login.getToken());
    }

    @Test
    void getSellerInfoByUsername () {
        String username = "seller-cxk";
        SellerInfoDTO dto = sellerService.getSellerInfo(username);
        assertEquals("蔡徐坤",dto.getRealName());
        assertEquals("17359561234",dto.getPhone());
    }
}