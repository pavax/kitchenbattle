package ch.adrianos.apps.kitchenbattle.web;

import ch.adrianos.apps.kitchenbattle.domain.event.EventId;
import ch.adrianos.apps.kitchenbattle.domain.team.Team;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamId;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamRepository;
import ch.adrianos.apps.kitchenbattle.service.CreateTeamDto;
import ch.adrianos.apps.kitchenbattle.service.EventNotFoundException;
import ch.adrianos.apps.kitchenbattle.service.TeamNotFoundException;
import ch.adrianos.apps.kitchenbattle.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamRepository teamRepository;

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamRepository teamRepository, TeamService teamService) {
        this.teamRepository = teamRepository;
        this.teamService = teamService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    public List<Team> findTeamsForEvent(@RequestParam String eventId) {
        return teamRepository.findAllTeamsForEvent(new EventId(eventId));
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @Secured ({"ROLE_ADMIN"})
    public String createTeam(@RequestBody @Valid CreateTeamDto createTeamDto) throws EventNotFoundException {
        return teamService.createTeam(createTeamDto);
    }

    @RequestMapping(value = "/{teamId}", method = RequestMethod.GET)
    @Secured({"ROLE_ADMIN"})
    public Team getTeam(@PathVariable String teamId) throws TeamNotFoundException {
        Team team = teamRepository.findOne(new TeamId(teamId));
        if (team == null) {
            throw new TeamNotFoundException(teamId);
        }
        return team;
    }

    @RequestMapping(value = "/{teamId}", method = RequestMethod.DELETE)
    @Secured({"ROLE_ADMIN"})
    public void deleteTeam(@PathVariable String teamId) {
        teamService.deleteTeam(teamId);
    }

}
