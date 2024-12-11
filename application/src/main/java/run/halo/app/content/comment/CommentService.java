package run.halo.app.content.comment;

import org.springframework.lang.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import reactor.core.publisher.Mono;
import run.halo.app.core.extension.content.Comment;
import run.halo.app.extension.ListResult;
import run.halo.app.extension.Ref;
import run.halo.app.infra.config.RedisMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * An application service for {@link Comment}.
 *
 * @author StevenChen16
 * @since 2.0.0
 */
public interface CommentService {

    @Cacheable(value = "comments-list", 
        key = "{#query?.keyword, #query?.ownerKind, #query?.ownerName, #query?.page, #query?.size, #query?.sort}")
    Mono<ListResult<ListedComment>> listComment(CommentQuery query);

    @CacheEvict(value = {"comments-list"}, allEntries = true)
    default Mono<Comment> create(Comment comment) {
        return Mono.defer(() -> {
            // Publish cache invalidation message
            redisMessagePublisher.publish("comments-list");
            return createComment(comment);
        });
    }

    @CacheEvict(value = {"comments-list"}, allEntries = true)
    default Mono<Void> removeBySubject(@NonNull Ref subjectRef) {
        return Mono.defer(() -> {
            // Publish cache invalidation message
            redisMessagePublisher.publish("comments-list");
            return removeCommentBySubject(subjectRef);
        });
    }

    // Method to create a comment
    Mono<Comment> createComment(Comment comment);

    // Method to remove a comment by subject
    Mono<Void> removeCommentBySubject(@NonNull Ref subjectRef);

    @Autowired
    RedisMessagePublisher redisMessagePublisher;
}
