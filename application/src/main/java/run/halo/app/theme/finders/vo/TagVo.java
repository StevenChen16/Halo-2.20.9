package run.halo.app.theme.finders.vo;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import lombok.Builder;
import lombok.Value;
import run.halo.app.core.extension.content.Tag;
import run.halo.app.extension.MetadataOperator;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * A value object for {@link Tag}.
 */
@Value
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagVo implements ExtensionVoOperator {

    MetadataOperator metadata;

    Tag.TagSpec spec;

    Tag.TagStatus status;

    Integer postCount;

    /**
     * Convert {@link Tag} to {@link TagVo}.
     *
     * @param tag tag extension
     * @return tag value object
     */
    public static TagVo from(Tag tag) {
        Tag.TagSpec spec = tag.getSpec();
        Tag.TagStatus status = tag.getStatusOrDefault();
        return TagVo.builder()
            .metadata(tag.getMetadata())
            .spec(spec)
            .status(status)
            .postCount(defaultIfNull(status.getVisiblePostCount(), 0))
            .build();
    }
}
