// run.halo.app.infra.cache.CacheInvalidationMessage.java
package run.halo.app.infra.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CacheInvalidationMessage {
    private String cacheNames; // 以逗号分隔的缓存名称
    private String key; // 可选的特定要失效的key
    private boolean allEntries; // 是否清除所有条目
}