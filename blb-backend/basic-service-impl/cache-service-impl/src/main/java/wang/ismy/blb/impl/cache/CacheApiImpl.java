package wang.ismy.blb.impl.cache;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.api.cache.CacheApi;
import wang.ismy.blb.common.Result;
import wang.ismy.blb.impl.cache.service.CacheService;
import wang.ismy.blb.impl.cache.service.CacheServiceImpl;

/**
 * @author MY
 * @date 2020/4/11 15:31
 */
@RestController
@AllArgsConstructor
public class CacheApiImpl implements CacheApi {

    private CacheService cacheService;

    @Override
    public Result<Void> put(String key, String data, Long ttl) {
        cacheService.put(key,data,ttl);
        return Result.success();
    }

    @Override
    public Result<String> get(String key) {
        return Result.success(cacheService.get(key));
    }

    @Override
    public Result<Boolean> exits(String key) {
        return Result.success(cacheService.exists(key));
    }

    @Override
    public Result<Void> delete(String key) {
        cacheService.delete(key);
        return Result.success();
    }
}
