package wang.ismy.blb.api.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wang.ismy.blb.common.Result;

import java.util.concurrent.TimeUnit;

/**
 * 一个简单的二级缓存：
 * 如果本地缓存没有命中，则调用缓存服务
 * @author MY
 * @date 2020/4/11 11:57
 */
@Slf4j
public class CacheService {

    protected CacheApi cacheApi;
    protected String serviceName;
    protected Cache<String,String> localCache;
    protected CacheSerializer cacheSerializer;

    public CacheService(CacheApi cacheApi, String serviceName, CacheSerializer cacheSerializer) {
        this.cacheApi = cacheApi;
        this.serviceName = serviceName;
        this.cacheSerializer = cacheSerializer;
    }

    private static final long LOCAL_CACHE_TTL = 60L;
    {
        localCache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(getLocalCacheTtl(), TimeUnit.SECONDS)
                .concurrencyLevel(10)
                .recordStats()
                .build();

    }

    /**
     * 本地缓存命中就不查询远程缓存了
     * @param key
     * @return
     */
    public <T> T get(String key,Class<T> returnType) {
        key = getKey(key);
        String origin = localCache.getIfPresent(key);
        if (origin == null){
            var remoteResult = cacheApi.get(key);
            if (!remoteResult.getSuccess()){
                log.error("调用远程缓存服务失败:{}",remoteResult.getMsg());
                return null;
            }
            origin = remoteResult.getData();
        }
        try {
            return cacheSerializer.deserialization(origin,returnType);
        } catch (Throwable throwable) {
            log.error("本地缓存反序列化发生错误,source:{}",throwable.getMessage());
            return null;
        }
    }

    /**
     * 如果本地缓存有，返回true，否则查询远程缓存
     * @param key
     * @return
     */
    public Boolean exists(String key) {
        key = getKey(key);
        if (localCache.getIfPresent(key) != null) {
            return true;
        }
        Result<Boolean> remoteResult = cacheApi.exits(key);
        return remoteResult.getData();
    }

    /**
     * 放入一个缓存时
     * 先将缓存放入本地缓存
     * 再将缓存放入远程缓存
     * 注意本地缓存的过期时间比远程缓存的时间还要早
     * @param key 缓存键
     * @param value 缓存值
     * @param ttl 单位为秒，必须大于1分钟
     * @return 缓存是否设置成功
     */
    public Boolean put(String key,Object value,Long ttl){
        key=getKey(key);
        if (ttl < LOCAL_CACHE_TTL) {
            throw new IllegalArgumentException("ttl不能小于" + getLocalCacheTtl());
        }
        String serialization;
        try {
            serialization = cacheSerializer.serialization(value);
            localCache.put(key, serialization);
        } catch (Throwable throwable) {
            log.error("本地缓存序列化异常,source:{},msg:{}",value,throwable.getMessage());
            return false;
        }
        var remoteResult = cacheApi.put(key,serialization,ttl);
        return remoteResult != null && remoteResult.getSuccess();
    }

    /**
     * 删除一个缓存
     * 先删除本地
     * 再删除远程
     * @param key
     * @return
     */
    public Boolean delete(String key){
        key = getKey(key);
        localCache.invalidate(key);
        var remoteResult = cacheApi.delete(key);
        return remoteResult != null && remoteResult.getSuccess();
    }

    /**
     * 子类可以重写这个方法自定义本地缓存过期时间
     * @return
     */
    protected long getLocalCacheTtl(){
        return LOCAL_CACHE_TTL;
    }

    private String getKey(String key) {
        key = serviceName + "-"+ key;
        return key;
    }
}
