// run.halo.app.infra.cache.RedisMessagePublisher.java
package run.halo.app.infra.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisMessagePublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(RedisMessagePublisher.class);

    public RedisMessagePublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void publishCacheInvalidation(String[] cacheNames, String key, boolean allEntries) {
        try {
            var message = new CacheInvalidationMessage(
                String.join(",", cacheNames),
                key,
                allEntries
            );
            String messageJson = objectMapper.writeValueAsString(message);
            redisTemplate.convertAndSend("halo:cache:invalidation", messageJson);
            
            log.debug("发布缓存失效消息: {}", messageJson);
        } catch (Exception e) {
            log.error("发布缓存失效消息失败", e);
        }
    }
}