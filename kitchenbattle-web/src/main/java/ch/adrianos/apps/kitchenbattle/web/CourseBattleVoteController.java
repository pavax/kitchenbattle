package ch.adrianos.apps.kitchenbattle.web;

import ch.adrianos.apps.kitchenbattle.domain.coursebattle.BattleId;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseId;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattleVote;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattleVoteRepository;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/course-battle-votes")
public class CourseBattleVoteController {

    private final CourseBattleVoteRepository courseBattleVoteRepository;

    @Autowired
    public CourseBattleVoteController(CourseBattleVoteRepository courseBattleVoteRepository) {
        this.courseBattleVoteRepository = courseBattleVoteRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void vote(@RequestBody @Valid CreateGuestCourseBattleVotingDto createGuestCourseBattleVotingDto) {
        courseBattleVoteRepository.save(new CourseBattleVote(new BattleId(createGuestCourseBattleVotingDto.getBattleId()), new CourseId(createGuestCourseBattleVotingDto.getVotedCourseId())));
    }

    public static class CreateGuestCourseBattleVotingDto {

        @NotBlank
        private String battleId;

        @NotBlank
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
