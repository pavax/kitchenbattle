package ch.adrianos.apps.kitchenbattle.domain.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.AccessType;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@AccessType(AccessType.Type.FIELD)
public class CourseId implements Serializable {

    @Column(name = "courseId")
    @NotBlank
    @JsonProperty("courseId")
    private String value;

    public CourseId(String value) {
        Assert.notNull(value);
        this.value = value;
    }

    public CourseId() {
        this.value = UUID.randomUUID().toString();
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CourseId{");
        sb.append("value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseId courseId = (CourseId) o;

        if (!value.equals(courseId.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
