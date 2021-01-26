package cn.xanderye.config;

import cn.xanderye.constant.Constants;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


@Configuration
public class EhcacheConfig {

    @Bean
    public CacheManager cacheManager() {

        // 配置默认缓存属性
        CacheConfiguration<String, Long> cacheConfigurationFile = CacheConfigurationBuilder.newCacheConfigurationBuilder(
                String.class, Long.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                        //设置缓存堆容纳元素个数(JVM内存空间)超出个数后会存到offheap中
                        //设置堆外储存大小(内存存储) 超出offheap的大小会淘汰规则被淘汰
                        .offheap(100L, MemoryUnit.MB)

        ).withExpiry(
                // 30分钟过期
                Expirations.timeToIdleExpiration(Duration.of(30, TimeUnit.MINUTES))
        ).build();


        // CacheManager管理缓存
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                // 设置一个默认缓存配置
                .withCache(Constants.EHCACHE_SHARE_CACHE_NAME, cacheConfigurationFile)
                //创建之后立即初始化
                .build(true);
        return cacheManager;
    }
}
