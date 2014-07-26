package ch.adrianos.apps.kitchenbattle.service;

import org.hibernate.validator.constraints.NotBlank;

public class CreateTeamDto {

    @NotBlank
    private String name;

    private String color;

    private String description;

    @NotBlank
    private String eventId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
