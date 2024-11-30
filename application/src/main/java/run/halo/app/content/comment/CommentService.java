package run.halo.app.content.comment;

import org.springframework.lang.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import reactor.core.publisher.Mono;
import run.halo.app.core.extension.content.Comment;
import run.halo.app.extension.ListResult;
import run.halo.app.extension.Ref;

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
    Mono<Comment> create(Comment comment);

    @CacheEvict(value = {"comments-list"}, allEntries = true)
    Mono<Void> removeBySubject(@NonNull Ref subjectRef);
}
