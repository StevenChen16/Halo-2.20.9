package run.halo.app.content;

import org.springframework.lang.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import run.halo.app.core.extension.content.Post;
import run.halo.app.extension.ListResult;
import run.halo.app.infra.config.RedisMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service for {@link Post}.
 *
 * @author StevenChen16
 * @since 2.20.10
 */
public interface PostService {
    // 文章列表缓存,使用查询参数作为key
    @Cacheable(value = "posts-list", 
        key = "T(String).format('list:%s:%d:%d:%s', " + 
              "#query.categoryWithChildren, #query.page, #query.size, #query.keyword)",
        unless = "#result == null")
    Mono<ListResult<ListedPost>> listPost(PostQuery query);

    // 清除所有文章相关缓存
    @CacheEvict(value = {"posts", "posts-list", "post-contents"}, allEntries = true)
    Mono<Post> draftPost(PostRequest postRequest);

    @CacheEvict(value = {"posts", "posts-list", "post-contents"}, allEntries = true)
    Mono<Post> updatePost(PostRequest postRequest);

    @CacheEvict(value = {"posts", "posts-list", "post-contents"}, allEntries = true)
    Mono<Post> updateBy(@NonNull Post post);

    // 文章内容缓存
    @Cacheable(value = "posts", 
        key = "'head:' + #postName", 
        unless = "#result == null")
    Mono<ContentWrapper> getHeadContent(String postName);

    @Cacheable(value = "posts", 
        key = "'head:' + #post.metadata.name",
        unless = "#result == null")
    Mono<ContentWrapper> getHeadContent(Post post);

    @Cacheable(value = "posts", 
        key = "'release:' + #postName",
        unless = "#result == null")
    Mono<ContentWrapper> getReleaseContent(String postName);

    @Cacheable(value = "posts", 
        key = "'release:' + #post.metadata.name",
        unless = "#result == null")
    Mono<ContentWrapper> getReleaseContent(Post post);

    @Cacheable(value = "post-contents", 
        key = "#snapshotName + ':' + #baseSnapshotName",
        unless = "#result == null")
    Mono<ContentWrapper> getContent(String snapshotName, String baseSnapshotName);

    @Cacheable(value = "post-snapshots", 
        key = "#name",
        unless = "#result == null")
    Flux<ListedSnapshotDto> listSnapshots(String name);

    // 更新操作清除所有相关缓存
    @CacheEvict(value = {"posts", "posts-list", "post-contents"}, allEntries = true)
    Mono<Post> publish(Post post);

    @CacheEvict(value = {"posts", "posts-list", "post-contents"}, allEntries = true)
    Mono<Post> unpublish(Post post);

    @Cacheable(value = "posts", 
        key = "'user:' + #postName + ':' + #username",
        unless = "#result == null")
    Mono<Post> getByUsername(String postName, String username);

    @CacheEvict(value = {"posts", "posts-list", "post-contents"}, allEntries = true)
    Mono<Post> revertToSpecifiedSnapshot(String postName, String snapshotName);

    @CacheEvict(value = {"posts", "posts-list", "post-contents", "post-snapshots"}, 
        allEntries = true)
    Mono<ContentWrapper> deleteContent(String postName, String snapshotName);

    @CacheEvict(value = {"posts", "posts-list", "post-contents", "post-snapshots"}, 
        allEntries = true)
    Mono<Post> recycleBy(String postName, String username);
}
