// run.halo.app.infra.cache.CacheEvictionAspect.java
package run.halo.app.infra.cache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheEvictionAspect {
    private final RedisMessagePublisher messagePublisher;
    private static final Logger log = LoggerFactory.getLogger(CacheEvictionAspect.class);

    public CacheEvictionAspect(RedisMessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    @AfterReturning("@annotation(cacheEvict)")
    public void handleCacheEvict(JoinPoint jp, CacheEvict cacheEvict) {
        try {
            messagePublisher.publishCacheInvalidation(
                cacheEvict.value(),
                null,
                cacheEvict.allEntries()
            );
        } catch (Exception e) {
            log.error("处理缓存失效失败", e);
        }
    }
}