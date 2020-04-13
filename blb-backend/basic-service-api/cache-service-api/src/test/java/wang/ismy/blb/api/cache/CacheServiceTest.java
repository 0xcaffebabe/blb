package wang.ismy.blb.api.cache;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wang.ismy.blb.common.result.Result;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class CacheServiceTest {

    @Test
    @DisplayName("测试获取缓存")
    void testGet() throws Throwable {
        String key = "test-cache";
        String successResult = "success result";
        String serviceName = "servicea";

        CacheApi mockCacheApi = mock(CacheApi.class);
        CacheSerializer mockSerializer = mock(CacheSerializer.class);
        when(mockCacheApi.get(eq(serviceName+"-"+key))).thenReturn(Result.success(successResult));
        when(mockSerializer.deserialization(eq(successResult),eq(String.class))).thenReturn(successResult);

        CacheService cacheService = new CacheService(mockCacheApi, serviceName,mockSerializer);
        String result = cacheService.get(key,String.class);
        assertEquals(successResult,result);
    }

    @Test
    @DisplayName("测试获取本地缓存")
    void testGetLocalCache() throws Throwable {
        String key = "test-cache";
        String successResult = "success result";
        String serviceName = "servicea";

        CacheApi mockCacheApi = mock(CacheApi.class);
        CacheSerializer mockSerializer = mock(CacheSerializer.class);
        when(mockCacheApi.get(eq(serviceName+"-"+key))).thenReturn(Result.success(successResult));
        when(mockSerializer.deserialization(eq(successResult),eq(String.class))).thenReturn(successResult);
        when(mockSerializer.serialization(eq(successResult))).thenReturn(successResult);

        CacheService cacheService = new CacheService(mockCacheApi, serviceName,mockSerializer);
        cacheService.put(key,successResult,60L);
        String result = cacheService.get(key,String.class);
        assertEquals(successResult,result);
    }

    @Test
    @DisplayName("测试测试缓存是否存在")
    void testExists() throws Throwable {
        String key = "test-cache";
        String serviceName = "servicea";

        CacheApi mockCacheApi = mock(CacheApi.class);
        CacheSerializer mockSerializer = mock(CacheSerializer.class);
        when(mockCacheApi.exits(eq(serviceName+"-"+key))).thenReturn(Result.success(true));
        when(mockSerializer.serialization(any())).thenReturn("contenta");

        CacheService cacheService = new CacheService(mockCacheApi,serviceName,mockSerializer);
        cacheService.put("testx","content",60L);
        assertTrue(cacheService.exists("testx"));
        assertTrue(cacheService.exists(key));
    }

    @Test
    @DisplayName("测试缓存存放")
    void testPut() throws Throwable {

        assertThrows(IllegalArgumentException.class,()->{
            new CacheService(null,null,null).put("1","2",59L);
        });

        String key = "test-cache";
        String serviceName = "servicea";

        CacheApi mockCacheApi = mock(CacheApi.class);
        CacheSerializer mockSerializer = mock(CacheSerializer.class);
        when(mockSerializer.serialization(any())).thenReturn("value");

        CacheService cacheService = new CacheService(mockCacheApi,serviceName,mockSerializer);
        cacheService.put(key,"value",60L);

        verify(mockSerializer).serialization(eq("value"));
        verify(mockCacheApi).put(eq(serviceName+"-"+key),eq("value"),eq(60L));
    }

    @Test
    @DisplayName("测试删除缓存")
    void testDelete() throws Throwable {
        String key = "test-cache";
        String serviceName = "servicea";

        CacheApi mockCacheApi = mock(CacheApi.class);
        CacheSerializer mockSerializer = mock(CacheSerializer.class);
        when(mockCacheApi.delete(any())).thenReturn(Result.success(null));
        CacheService cacheService = new CacheService(mockCacheApi,serviceName,mockSerializer);
        assertTrue(cacheService.delete(key));
        verify(mockCacheApi).delete(eq(serviceName+"-"+key));

    }
}