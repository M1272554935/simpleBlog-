package com.mtw.blog_vue.utils;

import com.google.common.cache.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CacheManager {

    private static final Logger LOGGER = LogManager.getLogger(CacheManager.class);


    /**
     * 单例
     */
    private static class CacheManagerHandler{
            public final static CacheManager Single = new CacheManager();
    }

    public static CacheManager getInstance(){
        return CacheManagerHandler.Single;
    }

    /** 缓存项最大数量 */
    private static final Integer GUAVA_CACHE_SIZE = 1024;

    /** 缓存时间：天 */
    private static final Integer GUAVA_CACHE_DAY = 1;

    /** 缓存操作对象 */
    private static LoadingCache<String, Integer> GLOBAL_CACHE = null;

    static {
        try {
            GLOBAL_CACHE = loadCache(new CacheLoader<String, Integer>() {
                @Override
                public Integer load(String key) throws Exception {
                    // 处理缓存键不存在缓存值时的处理逻辑
                    return 0;
                }
            });
        } catch (Exception e) {
            LOGGER.error("初始化Guava Cache出错", e);

        }
    }

    /**
     * 全局缓存设置
     * 缓存项最大数量：1024
     * 缓存有效时间（天）：1
     *
     */
    private static LoadingCache<String,Integer> loadCache(CacheLoader<String,Integer> cacheLoader) throws Exception {
        LoadingCache<String,Integer> cache = CacheBuilder.newBuilder()
                //缓存池大小，在缓存项接近该大小时， Guava开始回收旧的缓存项
                .maximumSize(GUAVA_CACHE_SIZE)
                //设置时间对象没有被读/写访问则对象从内存中删除(在另外的线程里面不定期维护)
                .expireAfterAccess(GUAVA_CACHE_DAY, TimeUnit.DAYS)
                // 设置缓存在写入之后 设定时间 后失效
                .expireAfterWrite(GUAVA_CACHE_DAY, TimeUnit.DAYS)
                //移除监听器,缓存项被移除时会触发
                .removalListener(new RemovalListener<String,Integer>() {
                    @Override
                    public void onRemoval(RemovalNotification<String,Integer> rn) {
                        //逻辑操作
                    }
                })
                //开启Guava Cache的统计功能
                .recordStats()
                .build(cacheLoader);
        return cache;
    }

    /**
     * 设置缓存值
     * 注: 若已有该key值，则会先移除(会触发removalListener移除监听器)，再添加
     *
     */
    public  void put(String key,Integer  value) {
        try {
            GLOBAL_CACHE.put(key, value);
        } catch (Exception e) {
            LOGGER.error("设置缓存值出错", e);
        }
    }

    /**
     * 批量设置缓存值
     *
     */
    public  void putAll(Map<? extends String, ? extends Integer> map) {
        try {
            GLOBAL_CACHE.putAll(map);
        } catch (Exception e) {
            LOGGER.error("批量设置缓存值出错", e);
        }
    }

    /**
     * 获取缓存值
     * 注：如果键不存在值，将调用CacheLoader的load方法加载新值到该键中
     */
    public  Integer get(String key) {
        int token = 0;
        try {
            token = GLOBAL_CACHE.get(key);
        } catch (Exception e) {
            LOGGER.error("获取缓存值出错", e);
        }
        return token;
    }

    /**
     * 移除缓存
     *
     * @param key
     */
    public  void remove(String key) {
        try {
            GLOBAL_CACHE.invalidate(key);
        } catch (Exception e) {
            LOGGER.error("移除缓存出错", e);
        }
    }

    /**
     * 批量移除缓存
     *
     * @param keys
     */
    public  void removeAll(Iterable<String> keys) {
        try {
            GLOBAL_CACHE.invalidateAll(keys);
        } catch (Exception e) {
            LOGGER.error("批量移除缓存出错", e);
        }
    }

    /**
     * 清空所有缓存
     */
    public  void removeAll() {
        try {
            GLOBAL_CACHE.invalidateAll();
        } catch (Exception e) {
            LOGGER.error("清空所有缓存出错", e);
        }
    }

    /**
     * 获取缓存项数量
     *
     * @return
     */
    public long size() {
        long size = 0;
        try {
            size =  GLOBAL_CACHE.size();
        } catch (Exception e) {
            LOGGER.error("获取缓存项数量出错", e);
        }
        return size;
    }
}
