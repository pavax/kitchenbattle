package ch.adrianos.apps.kitchenbattle.service;

public interface TeamService {

    String createTeam(CreateTeamDto createTeamDto);

    void deleteTeam(String teamId);
}
