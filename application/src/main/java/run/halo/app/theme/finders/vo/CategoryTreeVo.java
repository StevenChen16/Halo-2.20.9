package run.halo.app.theme.finders.vo;

import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.util.Assert;
import run.halo.app.core.extension.content.Category;
import run.halo.app.extension.MetadataOperator;

@Data
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTreeVo implements VisualizableTreeNode<CategoryTreeVo>, ExtensionVoOperator {
    private MetadataOperator metadata;
    private Category.CategorySpec spec;
    private Category.CategoryStatus status;
    private List<CategoryTreeVo> children;
    private String parentName;
    private Integer postCount;

    /**
     * Convert {@link CategoryVo} to {@link CategoryTreeVo}.
     *
     * @param category category value object
     * @return category tree value object
     */
    public static CategoryTreeVo from(CategoryVo category) {
        Assert.notNull(category, "The category must not be null");
        return CategoryTreeVo.builder()
            .metadata(category.getMetadata())
            .spec(category.getSpec())
            .status(category.getStatus())
            .children(List.of())
            .postCount(Objects.requireNonNullElse(category.getPostCount(), 0))
            .build();
    }

    /**
     * Convert {@link CategoryTreeVo} to {@link CategoryVo}.
     */
    public static CategoryVo toCategoryVo(CategoryTreeVo categoryTreeVo) {
        Assert.notNull(categoryTreeVo, "The category tree vo must not be null");
        return CategoryVo.builder()
            .metadata(categoryTreeVo.getMetadata())
            .spec(categoryTreeVo.getSpec())
            .status(categoryTreeVo.getStatus())
            .postCount(categoryTreeVo.getPostCount())
            .build();
    }

    @Override
    public String nodeText() {
        return String.format("%s (%s)%s", getSpec().getDisplayName(), getPostCount(),
            spec.isPreventParentPostCascadeQuery() ? " (Independent)" : "");
    }
}
