package wang.ismy.blb.api.cache;

import com.google.gson.Gson;

/**
 * 提供的一个简单的json序列化器
 * @author MY
 * @date 2020/4/11 13:19
 */
public class JsonCacheSerializer implements CacheSerializer {

    @Override
    public String serialization(Object obj) throws Throwable {
        return new Gson().toJson(obj);
    }

    @Override
    public <T> T deserialization(String origin, Class<T> returnType) throws Throwable {
        return new Gson().fromJson(origin,returnType);
    }
}
