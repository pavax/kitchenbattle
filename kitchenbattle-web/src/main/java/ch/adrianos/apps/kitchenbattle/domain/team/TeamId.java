package ch.adrianos.apps.kitchenbattle.domain.team;

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
public class TeamId implements Serializable {

    @Column(name = "teamId")
    @NotBlank
    @JsonProperty("teamId")
    private String value;

    public TeamId(String value) {
        Assert.notNull(value);
        this.value = value;
    }

    public TeamId() {
        this.value = UUID.randomUUID().toString();
    }

    public String getValue() {
        return value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamId teamId = (TeamId) o;

        if (value != null ? !value.equals(teamId.value) : teamId.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
