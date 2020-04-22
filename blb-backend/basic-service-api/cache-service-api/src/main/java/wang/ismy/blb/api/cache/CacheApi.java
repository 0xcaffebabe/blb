package wang.ismy.blb.api.cache;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.result.Result;


/**
 * @author MY
 * @date 2020/4/11 11:45
 */
@Api(tags = "缓存服务接口")
@RequestMapping("v1/api")
public interface CacheApi {
    /**
     * 缓存存入
     * @param key
     * @param ttl
     * @param data
     * @return
     */
    @ApiOperation("缓存存入")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "key", dataType = "String", required = true, value = "缓存键"),
            @ApiImplicitParam(paramType = "query", name = "ttl", dataType = "Long", required = true, value = "缓存有效时间")
//            @ApiImplicitParam(paramType = "body", dataType = "String", required = true, value = "缓存值")
    })
    @PutMapping("{key}")
    Result<Void> put(@PathVariable("key") String key,
                     @RequestBody String data,
                     @RequestParam("ttl") Long ttl
                     );

    /**
     * 缓存读取
     * @param key
     * @return
     */
    @ApiOperation("缓存读取")
    @ApiImplicitParam(paramType = "path", name = "key", dataType = "String", required = true, value = "缓存键")
    @GetMapping("{key}")
    Result<String> get(@PathVariable String key);

    /**
     * 缓存是否存在
     * @param key
     * @return
     */
    @ApiOperation("缓存是否存在")
    @ApiImplicitParam(paramType = "path", name = "key", dataType = "String", required = true, value = "缓存键")
    @GetMapping("{key}/exists")
    Result<Boolean> exits(@PathVariable("key") String key);

    @ApiOperation("删除缓存")
    @ApiImplicitParam(paramType = "path", name = "key", dataType = "String", required = true, value = "缓存键")
    @DeleteMapping("{key}")
    Result<Void> delete(@PathVariable("key") String key);

    class Fallback implements CacheApi {

        @Override
        public Result<Void> put(String key, String data, Long ttl) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用缓存服务存缓存接口失败");
        }

        @Override
        public Result<String> get(String key) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用缓存服务取缓存接口失败");
        }

        @Override
        public Result<Boolean> exits(String key) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用缓存服务缓存是否存在接口失败");
        }

        @Override
        public Result<Void> delete(String key) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用缓存服务删除缓存接口失败");
        }
    }
}
