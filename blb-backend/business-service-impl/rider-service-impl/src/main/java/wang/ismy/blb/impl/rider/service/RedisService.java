package wang.ismy.blb.impl.rider.service;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author MY
 * @date 2020/4/29 9:27
 */
@Service
@AllArgsConstructor
public class RedisService {

    private StringRedisTemplate redisTemplate;

    public boolean setIfNotExists(String key,String value){
        return redisTemplate.opsForValue().setIfAbsent(key,value,30L, TimeUnit.SECONDS);
    }
}
