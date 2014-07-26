package ch.adrianos.apps.kitchenbattle.domain.event;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Event {

    @EmbeddedId
    @Valid
    @NotNull
    @JsonUnwrapped
    private EventId id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate eventDate;

    public Event(EventId id, String name, LocalDate eventDate) {
        this.id = id;
        this.name = name;
        this.eventDate = eventDate;
    }

    protected Event() {
    }

    public EventId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (id != null ? !id.equals(event.id) : event.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Event{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", eventDate=").append(eventDate);
        sb.append('}');
        return sb.toString();
    }
}
