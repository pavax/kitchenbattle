package ch.adrianos.apps.kitchenbattle.domain.team;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.AccessType;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;


@Embeddable
@AccessType(AccessType.Type.FIELD)
public class TeamId implements Serializable{

    @Column(name = "teamId")
    @NotBlank
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
}
