package wang.ismy.blb.impl.cache.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import wang.ismy.blb.impl.cache.dao.CacheDao;

/**
 * @author MY
 * @date 2020/4/11 15:48
 */
@Service
public class CacheServiceImpl implements CacheService{

    private CacheDao cacheDao;

    public CacheServiceImpl(CacheDao cacheDao) {
        this.cacheDao = cacheDao;
    }

    @Override
    public void put(String key, String data, Long ttl){
        assertKeyNotEmpty(key);
        if (ttl == null || ttl <= 0){
            throw new IllegalArgumentException("ttl不得小于等于0");
        }
        cacheDao.put(key,data,ttl);
    }

    @Override
    public String get(String key) {
        assertKeyNotEmpty(key);
        return cacheDao.get(key);
    }

    @Override
    public boolean exists(String key){
        assertKeyNotEmpty(key);
        return cacheDao.exists(key);
    }

    @Override
    public void delete(String key) {
        assertKeyNotEmpty(key);
        cacheDao.delete(key);
    }

    private void assertKeyNotEmpty(String key) {
        if (StringUtils.isEmpty(key)){
            throw new IllegalArgumentException("key不能为null");
        }
    }
}
