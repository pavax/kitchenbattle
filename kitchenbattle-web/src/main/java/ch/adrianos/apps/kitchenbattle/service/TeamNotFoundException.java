package ch.adrianos.apps.kitchenbattle.service;

public class TeamNotFoundException extends Exception {

    private final String teamId;

    public TeamNotFoundException(String teamId) {
        this.teamId = teamId;
    }
}
