package com.boldyrev.financialhelper.config;

import java.time.Duration;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

/**
 * Cache configuration.
 *
 * @author Alexandr Boldyrev
 */
@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(60))
            .disableCachingNullValues()
            .serializeValuesWith(
                SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
            .withCacheConfiguration("defaultCategory",
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(1)))
            .withCacheConfiguration("categoriesMap",
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(1)))
            .withCacheConfiguration("categories",
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(1)));
    }
}
