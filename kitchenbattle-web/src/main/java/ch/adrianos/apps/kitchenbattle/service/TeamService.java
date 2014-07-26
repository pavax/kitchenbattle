package ch.adrianos.apps.kitchenbattle.service;

public interface TeamService {

    String createTeam(CreateTeamDto createTeamDto) throws EventNotFoundException;

    void deleteTeam(String teamId);
}
