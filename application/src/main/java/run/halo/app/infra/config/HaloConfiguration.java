package run.halo.app.infra.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import java.io.IOException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import run.halo.app.infra.properties.HaloProperties;
import run.halo.app.search.lucene.LuceneSearchEngine;

@EnableCaching
@Configuration(proxyBeanMethods = false)
@EnableAsync
public class HaloConfiguration {

    @Bean
    Jackson2ObjectMapperBuilderCustomizer objectMapperCustomizer() {
        return builder -> {
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);
            builder.featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        };
    }

    @Bean
    @ConditionalOnProperty(prefix = "halo.search-engine.lucene", name = "enabled",
        havingValue = "true",
        matchIfMissing = true)
    LuceneSearchEngine luceneSearchEngine(HaloProperties haloProperties) throws IOException {
        return new LuceneSearchEngine(haloProperties.getWorkDir()
            .resolve("indices")
            .resolve("halo"));
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(redisConnectionFactory).build();
    }
}
