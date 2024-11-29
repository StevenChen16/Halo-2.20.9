package run.halo.app.content;

import org.springframework.lang.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import run.halo.app.core.extension.content.Post;
import run.halo.app.extension.ListResult;

/**
 * Service for {@link Post}.
 *
 * @author StevenChen6
 * @since 2.20.10
 */
public interface PostService {

    @Cacheable(value = "posts-list", key = "#query")
    Mono<ListResult<ListedPost>> listPost(PostQuery query);

    @CacheEvict(value = {"posts", "posts-list"}, allEntries = true)
    Mono<Post> draftPost(PostRequest postRequest);

    @CacheEvict(value = {"posts", "posts-list"}, allEntries = true)
    Mono<Post> updatePost(PostRequest postRequest);

    @CacheEvict(value = {"posts", "posts-list"}, allEntries = true)
    Mono<Post> updateBy(@NonNull Post post);

    @Cacheable(value = "posts", key = "'head:' + #postName")
    Mono<ContentWrapper> getHeadContent(String postName);

    @Cacheable(value = "posts", key = "'head:' + #post.metadata.name")
    Mono<ContentWrapper> getHeadContent(Post post);

    @Cacheable(value = "posts", key = "'release:' + #postName")
    Mono<ContentWrapper> getReleaseContent(String postName);

    @Cacheable(value = "posts", key = "'release:' + #post.metadata.name")
    Mono<ContentWrapper> getReleaseContent(Post post);

    @Cacheable(value = "post-contents", key = "#snapshotName + ':' + #baseSnapshotName")
    Mono<ContentWrapper> getContent(String snapshotName, String baseSnapshotName);

    @Cacheable(value = "post-snapshots", key = "#name")
    Flux<ListedSnapshotDto> listSnapshots(String name);

    @CacheEvict(value = {"posts", "posts-list"}, allEntries = true)
    Mono<Post> publish(Post post);

    @CacheEvict(value = {"posts", "posts-list"}, allEntries = true)
    Mono<Post> unpublish(Post post);

    /**
     * Get post by username.
     *
     * @param postName is post name.
     * @param username is username.
     * @return full post data or empty.
     */
    @Cacheable(value = "posts", key = "#postName + ':' + #username")
    Mono<Post> getByUsername(String postName, String username);

    @CacheEvict(value = {"posts", "posts-list", "post-contents"}, allEntries = true)
    Mono<Post> revertToSpecifiedSnapshot(String postName, String snapshotName);

    @CacheEvict(value = {"posts", "posts-list", "post-contents", "post-snapshots"}, 
        key = "#postName", allEntries = true)
    Mono<ContentWrapper> deleteContent(String postName, String snapshotName);

    @CacheEvict(value = {"posts", "posts-list", "post-contents", "post-snapshots"}, 
        key = "#postName", allEntries = true)
    Mono<Post> recycleBy(String postName, String username);
}