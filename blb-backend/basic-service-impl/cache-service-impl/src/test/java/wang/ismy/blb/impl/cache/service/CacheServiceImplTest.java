package wang.ismy.blb.impl.cache.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import wang.ismy.blb.impl.cache.dao.CacheDao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class CacheServiceImplTest {

    @Test
    @DisplayName("测试get")
    void testGet(){
        CacheDao mockCacheDao = mock(CacheDao.class);
        CacheService cacheService = new CacheServiceImpl(mockCacheDao);
        String key = "key";
        cacheService.get(key);
        verify(mockCacheDao).get(eq(key));

        assertThrows(IllegalArgumentException.class,()->{
            cacheService.get("");
        });
    }

    @Test
    @DisplayName("测试put")
    void testPut() {
        CacheDao mockCacheDao = mock(CacheDao.class);
        CacheService cacheService = new CacheServiceImpl(mockCacheDao);
        String key = "key";
        String data = "1";
        Long ttl = 60L;
        cacheService.put(key,data,ttl);

        verify(mockCacheDao).put(eq(key),eq(data),eq(ttl));

        assertThrows(IllegalArgumentException.class,()->{
            cacheService.put("",data,ttl);
        },"key不能为null");
        assertThrows(IllegalArgumentException.class,()->{
            cacheService.put("",data,0L);
        },"ttl不得小于等于0");
    }

    @Test
    @DisplayName("测试exits")
    void testExists(){
        CacheDao mockCacheDao = mock(CacheDao.class);
        CacheService cacheService = new CacheServiceImpl(mockCacheDao);
        String key = "key";
        cacheService.exists(key);
        verify(mockCacheDao).exists(eq(key));

        assertThrows(IllegalArgumentException.class,()->{
            cacheService.exists("");
        },"key不能为null");
    }

    @Test
    @DisplayName("测试delete")
    void testDelete(){
        CacheDao mockCacheDao = mock(CacheDao.class);
        CacheService cacheService = new CacheServiceImpl(mockCacheDao);
        String key = "key";
        cacheService.delete(key);
        verify(mockCacheDao).delete(eq(key));

        assertThrows(IllegalArgumentException.class,()->{
            cacheService.delete("");
        },"key不能为null");
    }
}