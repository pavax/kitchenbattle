package ch.adrianos.apps.kitchenbattle.web;

import ch.adrianos.apps.kitchenbattle.domain.team.Team;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamId;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamRepository;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String createTeam(@RequestBody @Valid CreateTeamDto createTeamDto) {
        Team save = teamRepository.save(new Team(new TeamId(), createTeamDto.getName(), createTeamDto.getColor(), createTeamDto.getDescription()));
        return save.getTeamId().getValue();
    }

    @RequestMapping(value = "/{teamId}", method = RequestMethod.GET)
    public Team getTeam(@PathVariable String teamId) {
        Team team = teamRepository.findOne(new TeamId(teamId));
        return team;
    }

    public static class CreateTeamDto {

        @NotBlank
        private String name;

        private String color;

        private String description;

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
    }
}
