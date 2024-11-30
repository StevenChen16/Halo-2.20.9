package run.halo.app.theme.finders.vo;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
/**
 * Post archives by month.
 *
 * @author guqing
 * @since 2.0.0
 */
@Value
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostArchiveYearMonthVo {

    String month;

    List<ListedPostVo> posts;
}
