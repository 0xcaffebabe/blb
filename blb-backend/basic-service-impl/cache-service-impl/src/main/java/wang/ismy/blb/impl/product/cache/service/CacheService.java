package wang.ismy.blb.impl.product.cache.service;

/**
 * 定义缓存服务应该提供的接口
 *
 * @author MY
 * @date 2020/4/12 8:52
 */
public interface CacheService {
    void put(String key, String data, Long ttl);

    String get(String key);

    boolean exists(String key);

    void delete(String key);
}
