package wang.ismy.blb.impl.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import wang.ismy.blb.api.cache.CacheApi;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.enums.ServiceName;
import wang.ismy.blb.common.result.Result;

/**
 * @author MY
 * @date 2020/4/13 11:46
 */
@FeignClient(value = ServiceName.CACHE_SERVICE,fallback = CacheApiClient.CacheApiFallback.class)
public interface CacheApiClient extends CacheApi {
    class CacheApiFallback implements CacheApi{

        @Override
        public Result<Void> put(String key, String data, Long ttl) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }

        @Override
        public Result<String> get(String key) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }

        @Override
        public Result<Boolean> exits(String key) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }

        @Override
        public Result<Void> delete(String key) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
    }
}
