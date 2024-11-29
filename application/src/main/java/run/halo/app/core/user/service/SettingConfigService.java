package run.halo.app.core.user.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import reactor.core.publisher.Mono;
import run.halo.app.core.extension.Setting;
import run.halo.app.extension.ConfigMap;

/**
 * {@link Setting} related {@link ConfigMap} service.
 *
 * @author StevenChen16
 * @since 2.20.10
 */
public interface SettingConfigService {

    @CacheEvict(value = "settings", key = "#configMapName")
    Mono<Void> upsertConfig(String configMapName, ObjectNode configJsonData);

    @Cacheable(value = "settings", key = "#configMapName")
    Mono<ObjectNode> fetchConfig(String configMapName);
}