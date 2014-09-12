package ch.adrianos.apps.kitchenbattle.web;

import ch.adrianos.apps.kitchenbattle.domain.event.EventId;
import ch.adrianos.apps.kitchenbattle.domain.team.JuryTeamVote;
import ch.adrianos.apps.kitchenbattle.domain.team.JuryTeamVoteRepository;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamId;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.Path;
import java.util.List;

@RestController
@RequestMapping("/api/jury-team-votes")
public class JuryTeamVoteController {

    private final JuryTeamVoteRepository juryTeamVoteRepository;

    @Autowired
    public JuryTeamVoteController(JuryTeamVoteRepository juryTeamVoteRepository) {
        this.juryTeamVoteRepository = juryTeamVoteRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_ADMIN"})
    public void vote(@RequestBody @Valid CreateJuryTeamVoteDto createJuryTeamVoteDto) {
        juryTeamVoteRepository.save(new JuryTeamVote(createJuryTeamVoteDto.votes, createJuryTeamVoteDto.juryName, new TeamId(createJuryTeamVoteDto.teamId)));
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<JuryTeamVote> findJuryTeamVotesForEvent(@RequestParam String eventId) {
        return juryTeamVoteRepository.findAllJuryTeamVotesForEvent(new EventId(eventId));
    }

    @RequestMapping(value = "/{voteId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    public void deleteJuryVote(@PathVariable String voteId) {
        juryTeamVoteRepository.delete(voteId);
    }

    private static class CreateJuryTeamVoteDto {

        @NotBlank
        private String teamId;

        @NotBlank
        private String juryName;

        @Min(value = 1)
        private int votes;

        public String getTeamId() {
            return teamId;
        }

        public void setTeamId(String teamId) {
            this.teamId = teamId;
        }

        public String getJuryName() {
            return juryName;
        }

        public void setJuryName(String juryName) {
            this.juryName = juryName;
        }

        public int getVotes() {
            return votes;
        }

        public void setVotes(int votes) {
            this.votes = votes;
        }
    }
}
