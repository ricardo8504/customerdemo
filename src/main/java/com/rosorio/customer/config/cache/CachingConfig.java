package com.rosorio.customer.config.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {

    public static final String CUSTOMER_INFO_CACHE = "customerInfoCache";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CUSTOMER_INFO_CACHE);
    }
}
