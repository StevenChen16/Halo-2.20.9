package run.halo.app.content.comment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

/**
 * comment stats value object.
 *
 * @author LIlGG
 * @since 2.0.0
 */
@Value
@Builder
public class CommentStats {
    Integer upvote;

    @JsonCreator
    public CommentStats(@JsonProperty("upvote") Integer upvote) {
        this.upvote = upvote != null ? upvote : 0;
    }

    public static CommentStats empty() {
        return CommentStats.builder()
            .upvote(0)
            .build();
    }
}
