package run.halo.app.infra.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableAsync;
import run.halo.app.infra.properties.HaloProperties;
import run.halo.app.search.lucene.LuceneSearchEngine;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.ChannelTopic;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import run.halo.app.theme.finders.vo.ContributorVo;
import run.halo.app.infra.utils.JsonSerializer.ContributorVoSerDe;
import com.fasterxml.jackson.databind.ObjectMapper;
import run.halo.app.core.extension.content.Tag;
import run.halo.app.extension.MetadataOperator;

@EnableCaching
@Configuration(proxyBeanMethods = false)
@EnableAsync
public class HaloConfiguration {

    private static final Logger log = LoggerFactory.getLogger(HaloConfiguration.class);

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);
        if (redisPassword != null && !redisPassword.isEmpty()) {
            config.setPassword(redisPassword);
        }
        
        log.info("Connecting to Redis - Host: {}, Port: {}, Password: {}",
            redisHost, 
            redisPort,
            redisPassword != null ? "*".repeat(redisPassword.length()) : "not set");
            
        return new LettuceConnectionFactory(config);
    }

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
    public GenericJackson2JsonRedisSerializer redisSerializer() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        mapper.enable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS);
        
        mapper.registerModule(new JavaTimeModule());
        
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        mapper.configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, false);
        
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.enable(MapperFeature.USE_STD_BEAN_NAMING);
        mapper.enable(MapperFeature.AUTO_DETECT_FIELDS);
        mapper.enable(MapperFeature.AUTO_DETECT_GETTERS);
        mapper.enable(MapperFeature.AUTO_DETECT_IS_GETTERS);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY);
        
        SimpleModule module = new SimpleModule();
        module.addSerializer(ContributorVo.class, new ContributorVoSerDe.Serializer());
        module.addDeserializer(ContributorVo.class, new ContributorVoSerDe.Deserializer());
        mapper.registerModule(module);
        // 添加 TagVo 的序列化器
        module.addSerializer(TagVo.class, new TagVoSerDe.Serializer());
        module.addDeserializer(TagVo.class, new TagVoSerDe.Deserializer());
        mapper.registerModule(module);

        mapper.activateDefaultTyping(
            mapper.getPolymorphicTypeValidator(),
            ObjectMapper.DefaultTyping.NON_FINAL,
            JsonTypeInfo.As.PROPERTY
        );
        
        return new GenericJackson2JsonRedisSerializer(mapper);
    }

    @Bean
    public InitializingBean clearCache(RedisConnectionFactory redisConnectionFactory) {
        return () -> {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(redisConnectionFactory);
            template.setKeySerializer(new StringRedisSerializer());
            
            try {
                // 清理插件设置缓存
                Set<String> pluginKeys = template.keys("plugin_setting*");
                if (pluginKeys != null && !pluginKeys.isEmpty()) {
                    template.delete(pluginKeys);
                    log.info("Cleared plugin setting cache");
                }
                
                // 清理其他损坏的缓存
                Set<String> damagedKeys = template.keys("*¬*");
                if (damagedKeys != null && !damagedKeys.isEmpty()) {
                    template.delete(damagedKeys);
                    log.info("Cleared damaged cache entries");
                }
            } catch (Exception e) {
                log.warn("Failed to clear cache: {}", e.getMessage());
            }
        };
    }
    
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        GenericJackson2JsonRedisSerializer serializer = redisSerializer();
        
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(serializer)
            )
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    new StringRedisSerializer()
                )
            )
            .entryTtl(Duration.ofHours(1))
            .computePrefixWith(cacheName -> "halo:" + cacheName + ":");
    
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        
        cacheConfigurations.put("posts-list", 
            defaultConfig.entryTtl(Duration.ofMinutes(30)));
        cacheConfigurations.put("posts", 
            defaultConfig.entryTtl(Duration.ofHours(2)));
        cacheConfigurations.put("post-contents", 
            defaultConfig.entryTtl(Duration.ofHours(2)));
        cacheConfigurations.put("post-snapshots", 
            defaultConfig.entryTtl(Duration.ofHours(2)));
        cacheConfigurations.put("comments-list", 
            defaultConfig.entryTtl(Duration.ofMinutes(30)));
    
        return RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(defaultConfig)
            .withInitialCacheConfigurations(cacheConfigurations)
            .transactionAware()
            .build();
    }
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        
        GenericJackson2JsonRedisSerializer serializer = redisSerializer();
        
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        
        template.afterPropertiesSet();
        
        return template;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        return container;
    }

    @Bean
    public InitializingBean clearPostCache(RedisTemplate<String, Object> redisTemplate) {
        return () -> {
            try {
                Set<String> keys = redisTemplate.keys("halo:post:list:*");
                if (keys != null && !keys.isEmpty()) {
                    redisTemplate.delete(keys);
                    log.info("Cleared post cache during initialization, {} keys removed", keys.size());
                }
            } catch (Exception e) {
                log.warn("Failed to clear post cache during initialization: {}", e.getMessage());
            }
        };
    }
}
