package wang.ismy.blb.api.cache;

/**
 * 缓存序列化器接口
 * 可以自定义一些对象的序列化器
 * @author MY
 * @date 2020/4/11 13:15
 */
public interface CacheSerializer {

    /**
     * 负责将传入的对象转为字符串形式
     * @param obj
     * @throws Throwable 无法序列化产生的异常
     * @return
     */
    String serialization(Object obj) throws Throwable;

    /**
     * 负责将源字符串转为对象
     * @param origin
     * @param returnType 返回类型
     * @return
     * @throws Throwable
     */
    <T> T deserialization(String origin,Class<T> returnType) throws Throwable;
}
