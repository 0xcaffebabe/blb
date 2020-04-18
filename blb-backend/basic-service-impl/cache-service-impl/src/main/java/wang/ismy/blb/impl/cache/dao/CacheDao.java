package wang.ismy.blb.impl.cache.dao;

/**
 * @author MY
 * @date 2020/4/11 15:50
 */
public interface CacheDao {

    /**
     * 往缓存数据源存放数据
     * @param key
     * @param data
     * @param ttl
     */
    void put(String key, String data, Long ttl);

    /**
     * 从缓存数据源中取数据
     * @param key
     * @return 不存在则返回null
     */
    String get(String key);

    /**
     * 判断缓存是否存在
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 删除缓存
     * @param key
     */
    void delete(String key);
}
