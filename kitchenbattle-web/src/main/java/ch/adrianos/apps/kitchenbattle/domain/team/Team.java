package ch.adrianos.apps.kitchenbattle.domain.team;

import ch.adrianos.apps.kitchenbattle.domain.event.Event;
import ch.adrianos.apps.kitchenbattle.domain.event.EventId;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Team {

    @EmbeddedId
    @Valid
    @JsonUnwrapped
    private TeamId teamId;

    @Embedded
    @NotNull
    @JsonUnwrapped
    private EventId eventId;

    @NotBlank
    private String name;

    private String color;

    private String description;

    public Team(TeamId teamId, String name, String color, String description, Event event) {
        this.teamId = teamId;
        this.name = name;
        this.color = color;
        this.description = description;
        this.eventId = event.getId();
    }

    protected Team() {
    }

    public TeamId getTeamId() {
        return teamId;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public EventId getEventId() {
        return eventId;
    }
}
