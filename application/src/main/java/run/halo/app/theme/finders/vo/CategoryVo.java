package run.halo.app.theme.finders.vo;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import run.halo.app.core.extension.content.Category;
import run.halo.app.extension.MetadataOperator;

@Value
@Builder
@EqualsAndHashCode
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CategoryVo implements ExtensionVoOperator {
    MetadataOperator metadata;
    Category.CategorySpec spec;
    Category.CategoryStatus status;
    Integer postCount;

    /**
     * Convert {@link Category} to {@link CategoryVo}.
     *
     * @param category category extension
     * @return category value object
     */
    public static CategoryVo from(Category category) {
        return CategoryVo.builder()
            .metadata(category.getMetadata())
            .spec(category.getSpec())
            .status(category.getStatus())
            .postCount(category.getStatusOrDefault().getVisiblePostCount())
            .build();
    }
}
