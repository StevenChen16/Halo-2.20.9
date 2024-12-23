package run.halo.app.theme.finders.vo;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;
import run.halo.app.content.comment.OwnerInfo;
import run.halo.app.core.extension.content.Comment;
import run.halo.app.extension.MetadataOperator;

@Data
@Accessors(chain = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo implements ExtensionVoOperator {
    @Schema(requiredMode = REQUIRED)
    private MetadataOperator metadata;

    @Schema(requiredMode = REQUIRED)
    private Comment.CommentSpec spec;

    private Comment.CommentStatus status;

    @Schema(requiredMode = REQUIRED)
    private OwnerInfo owner;

    @Schema(requiredMode = REQUIRED)
    private CommentStatsVo stats;


    /**
     * Convert {@link Comment} to {@link CommentVo}.
     *
     * @param comment comment extension
     * @return a value object for {@link Comment}
     */
    public static CommentVo from(Comment comment) {
        return new CommentVo()
            .setMetadata(comment.getMetadata())
            .setSpec(comment.getSpec())
            .setStatus(comment.getStatus());
    }
}
