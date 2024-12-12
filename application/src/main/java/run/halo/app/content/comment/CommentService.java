// run.halo.app.content.comment.CommentService.java
package run.halo.app.content.comment;

import org.springframework.lang.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import reactor.core.publisher.Mono;
import run.halo.app.core.extension.content.Comment;
import run.halo.app.extension.ListResult;
import run.halo.app.extension.Ref;
import run.halo.app.infra.cache.RedisMessagePublisher; // 只保留一个import

/**
 * An application service for {@link Comment}.
 *
 * @author Guqing
 * @author StevenChen16
 * @since 2.20.11
 */
public interface CommentService {

    /**
     * List comments with cache.
     */
    @Cacheable(value = "comments-list", 
        key = "{#query?.keyword, #query?.ownerKind, #query?.ownerName, #query?.page, #query?.size, #query?.sort}")
    Mono<ListResult<ListedComment>> listComment(CommentQuery query);

    /**
     * Create a comment and invalidate cache.
     */
    @CacheEvict(value = {"comments-list"}, allEntries = true)
    Mono<Comment> create(Comment comment);

    /**
     * Remove comments by subject and invalidate cache.
     */
    @CacheEvict(value = {"comments-list"}, allEntries = true)
    Mono<Void> removeBySubject(@NonNull Ref subjectRef);
}