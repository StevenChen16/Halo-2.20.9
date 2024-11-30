package run.halo.app.theme.finders.vo;

import lombok.Builder;
import lombok.Value;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Value
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CommentStatsVo {
    Integer upvote;

    public static CommentStatsVo empty() {
        return CommentStatsVo.builder()
            .upvote(0)
            .build();
    }
}
