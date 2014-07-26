package ch.adrianos.apps.kitchenbattle.domain.event;

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
public class EventId implements Serializable {

    @Column(name = "eventId")
    @NotBlank
    @JsonProperty("eventId")
    private String value;

    public EventId(String value) {
        Assert.notNull(value);
        this.value = value;
    }

    public EventId() {
        this.value = UUID.randomUUID().toString();
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventId eventId = (EventId) o;

        if (!value.equals(eventId.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
