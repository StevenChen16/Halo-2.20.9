package run.halo.app.content;

import org.springframework.lang.NonNull;
import org.springframework.cache.annotation.Cacheable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import run.halo.app.core.extension.content.Category;

public interface CategoryService {

    @Cacheable(value = "categories", key = "'children:' + #categoryName")
    Flux<Category> listChildren(@NonNull String categoryName);

    @Cacheable(value = "categories", key = "'parent:' + #categoryName")
    Mono<Category> getParentByName(@NonNull String categoryName);

    @Cacheable(value = "categories", key = "'hidden:' + #categoryName")
    Mono<Boolean> isCategoryHidden(@NonNull String categoryName);
}