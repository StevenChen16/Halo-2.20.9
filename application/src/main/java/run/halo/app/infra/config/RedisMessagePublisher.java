package run.halo.app.infra.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisMessagePublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisMessagePublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publish(String message) {
        redisTemplate.convertAndSend("halo:cache:invalidation", message);
    }
}
