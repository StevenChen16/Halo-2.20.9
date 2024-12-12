// run.halo.app.infra.cache.RedisMessageSubscriber.java
package run.halo.app.infra.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RedisMessageSubscriber implements MessageListener {
    private final RedisCacheManager cacheManager;
    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(RedisMessageSubscriber.class);

    public RedisMessageSubscriber(RedisCacheManager cacheManager) {
        this.cacheManager = cacheManager;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String messageJson = new String(message.getBody());
            CacheInvalidationMessage invalidationMessage = 
                objectMapper.readValue(messageJson, CacheInvalidationMessage.class);

            String[] cacheNames = invalidationMessage.getCacheNames().split(",");
            
            for (String cacheName : cacheNames) {
                Cache cache = cacheManager.getCache(cacheName);
                if (cache != null) {
                    if (invalidationMessage.isAllEntries()) {
                        cache.clear();
                        log.debug("清除缓存 {} 的所有条目", cacheName);
                    } else if (invalidationMessage.getKey() != null) {
                        cache.evict(invalidationMessage.getKey());
                        log.debug("从缓存 {} 中清除key {}", 
                            cacheName, invalidationMessage.getKey());
                    }
                }
            }
        } catch (Exception e) {
            log.error("处理缓存失效消息失败", e);
        }
    }
}