package ch.adrianos.apps.kitchenbattle.domain.course;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class CourseVariant {

    @NotNull
    private String value;

    public CourseVariant(String value) {
        this.value = value;
    }

    protected CourseVariant() {
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseVariant that = (CourseVariant) o;

        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
