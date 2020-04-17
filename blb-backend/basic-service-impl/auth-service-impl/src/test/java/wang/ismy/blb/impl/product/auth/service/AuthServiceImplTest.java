package wang.ismy.blb.impl.product.auth.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.cache.CacheService;
import wang.ismy.blb.impl.product.auth.config.AuthConfigProperties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@RunWith(SpringRunner.class)
class AuthServiceImplTest {

    @Autowired
    AuthConfigProperties authConfigProperties;

    @Test
    void testAuth(){
        User user = new User();
        user.setUsername("cxk");
        CacheService cacheService = mock(CacheService.class);
        when(cacheService.put(any(),any(),anyLong())).thenReturn(true);

        AuthService authService = new AuthServiceImpl(cacheService,authConfigProperties);
        String token = authService.auth(user);
        verify(cacheService).put(eq(token),eq(user),eq(36000L));
    }

    @Test
    void testValid(){
        User user = new User();
        user.setUsername("cxk");
        CacheService cacheService = mock(CacheService.class);
        when(cacheService.get(eq("token"),eq(User.class))).thenReturn(user);

        AuthService authService = new AuthServiceImpl(cacheService,authConfigProperties);
        assertEquals(user,authService.valid("token"));
    }
}