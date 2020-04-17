package wang.ismy.blb.impl.product.cache.dao;

import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RedisCacheDaoTest {

    @Test
    void testGet() {
        String key = "key";
        String data = "value";
        StringRedisTemplate mockTemplate = mock(StringRedisTemplate.class);
        ValueOperations mockValue = mock(ValueOperations.class);
        when(mockValue.get(eq(key))).thenReturn(data);
        when(mockTemplate.opsForValue()).thenReturn(mockValue);

        CacheDao cacheDao = new RedisCacheDao(mockTemplate);
        assertEquals(data, cacheDao.get(key));
    }

    @Test
    void testPut() {
        String key = "key";
        String data = "value";
        Long ttl = 60L;
        StringRedisTemplate mockTemplate = mock(StringRedisTemplate.class);
        ValueOperations mockValue = mock(ValueOperations.class);
        when(mockTemplate.opsForValue()).thenReturn(mockValue);

        CacheDao cacheDao = new RedisCacheDao(mockTemplate);
        cacheDao.put(key, data, ttl);
        verify(mockValue).set(eq(key),eq(data),eq(ttl),eq(TimeUnit.SECONDS));
    }

    @Test
    void testExists() {
        String key = "key";
        String data = "value";
        Long ttl = 60L;
        StringRedisTemplate mockTemplate = mock(StringRedisTemplate.class);
        ValueOperations mockValue = mock(ValueOperations.class);
        when(mockTemplate.opsForValue()).thenReturn(mockValue);

        CacheDao cacheDao = new RedisCacheDao(mockTemplate);
        cacheDao.get(key);
        verify(mockValue).get(eq(key));
    }

    @Test
    void testDelete() {
        String key = "key";
        String data = "value";
        Long ttl = 60L;
        StringRedisTemplate mockTemplate = mock(StringRedisTemplate.class);
        ValueOperations mockValue = mock(ValueOperations.class);
        when(mockTemplate.opsForValue()).thenReturn(mockValue);

        CacheDao cacheDao = new RedisCacheDao(mockTemplate);
        cacheDao.delete(key);
        verify(mockTemplate).delete(eq(key));
    }
}