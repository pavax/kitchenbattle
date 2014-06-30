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
}
