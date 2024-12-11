package run.halo.app.content;

import org.springframework.lang.NonNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import run.halo.app.core.extension.content.Category;
import run.halo.app.infra.config.RedisMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;

public interface CategoryService {

    @Cacheable(value = "categories", key = "'children:' + #categoryName")
    Flux<Category> listChildren(@NonNull String categoryName);

    @Cacheable(value = "categories", key = "'parent:' + #categoryName")
    Mono<Category> getParentByName(@NonNull String categoryName);

    @Cacheable(value = "categories", key = "'hidden:' + #categoryName")
    Mono<Boolean> isCategoryHidden(@NonNull String categoryName);

    @CacheEvict(value = "categories", allEntries = true)
    default void evictAllCategoriesCache() {
        // This method will be used to evict all categories cache entries
    }
}
