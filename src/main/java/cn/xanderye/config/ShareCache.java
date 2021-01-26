package cn.xanderye.config;

import cn.xanderye.constant.Constants;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShareCache {
    @Autowired
    public CacheManager cacheManager;

    /**
     * 添加缓存
     *
     * @param key   关键字
     * @param value 值
     */
    public void put(String key, Long value) {

        //获取缓存容器
        Cache<String, Long> cache = cacheManager.getCache(Constants.EHCACHE_SHARE_CACHE_NAME, String.class, Long.class);

        cache.put(key, value);
    }

    /**
     * 获取键值
     *
     * @param key
     * @return
     */
    public Long get(String key) {

        try {

            //获取缓存容器
            Cache<String, Long> cache = cacheManager.getCache(Constants.EHCACHE_SHARE_CACHE_NAME, String.class, Long.class);

            return cache.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据key删除指定的cache中元素
     *
     * @throws Exception
     */

    public boolean removeElement(String key){
        //获取缓存容器
        Cache<String, Long> cache = cacheManager.getCache(Constants.EHCACHE_SHARE_CACHE_NAME, String.class, Long.class);

        //根据key删除指定元素
        cache.remove(key);
        return true;
    }
}
