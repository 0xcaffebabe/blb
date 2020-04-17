package wang.ismy.blb.impl.product.cache.dao;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * @author MY
 * @date 2020/4/12 9:02
 */
@Repository
@AllArgsConstructor
public class RedisCacheDao implements CacheDao{

    private StringRedisTemplate redisTemplate;

    @Override
    public void put(String key, String data, Long ttl) {
        redisTemplate.opsForValue().set(key,data,ttl, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.opsForValue().get(key) != null;
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
