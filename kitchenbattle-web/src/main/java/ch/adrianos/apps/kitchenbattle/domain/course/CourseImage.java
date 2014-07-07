package ch.adrianos.apps.kitchenbattle.domain.course;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class CourseImage {

    @Id
    private String id;

    @NotNull
    @Valid
    private CourseVariant variant;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Valid
    private Image image;

    public CourseImage(CourseVariant variant, Image image) {
        this.id = UUID.randomUUID().toString();
        this.variant = variant;
        this.image = image;
    }

    protected CourseImage() {
    }

    public CourseVariant getVariant() {
        return variant;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseImage that = (CourseImage) o;

        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (variant != null ? !variant.equals(that.variant) : that.variant != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = variant != null ? variant.hashCode() : 0;
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }
}
