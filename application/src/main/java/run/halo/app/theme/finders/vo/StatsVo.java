package run.halo.app.theme.finders.vo;

import lombok.Builder;
import lombok.Value;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Value
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class StatsVo {
    Integer visit;
    Integer upvote;
    Integer comment;

    public static StatsVo empty() {
        return StatsVo.builder()
            .visit(0)
            .upvote(0)
            .comment(0)
            .build();
    }
}