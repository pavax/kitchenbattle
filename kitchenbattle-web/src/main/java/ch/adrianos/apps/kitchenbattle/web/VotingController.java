package ch.adrianos.apps.kitchenbattle.web;

import ch.adrianos.apps.kitchenbattle.domain.battle.BattleId;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseId;
import ch.adrianos.apps.kitchenbattle.domain.voting.GuestCourseVoting;
import ch.adrianos.apps.kitchenbattle.domain.voting.GuestCourseVotingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votings")
public class VotingController {

    private final GuestCourseVotingRepository guestCourseVotingRepository;

    @Autowired
    public VotingController(GuestCourseVotingRepository guestCourseVotingRepository) {
        this.guestCourseVotingRepository = guestCourseVotingRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void vote(@RequestBody CreateGuestCourseVotingDto createGuestCourseVotingDto) {
        guestCourseVotingRepository.save(new GuestCourseVoting(new BattleId(createGuestCourseVotingDto.getBattleId()), new CourseId(createGuestCourseVotingDto.getVotedCourseId())));
    }

    public static class CreateGuestCourseVotingDto {

        private String battleId;

        private String votedCourseId;

        public String getBattleId() {
            return battleId;
        }

        public void setBattleId(String battleId) {
            this.battleId = battleId;
        }

        public String getVotedCourseId() {
            return votedCourseId;
        }

        public void setVotedCourseId(String votedCourseId) {
            this.votedCourseId = votedCourseId;
        }
    }
}
