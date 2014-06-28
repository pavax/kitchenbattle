package ch.adrianos.apps.kitchenbattle.domain.team;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.Valid;

@Entity
public class Team {

    @EmbeddedId
    @Valid
    private TeamId teamId;

    @NotBlank
    private String name;

    @NotBlank
    private String color;

    private String description;

    public Team(TeamId teamId, String name, String color, String description) {
        this.teamId = teamId;
        this.name = name;
        this.color = color;
        this.description = description;
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
}
