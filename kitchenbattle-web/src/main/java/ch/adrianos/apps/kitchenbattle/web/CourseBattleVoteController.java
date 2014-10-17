package ch.adrianos.apps.kitchenbattle.web;

import ch.adrianos.apps.kitchenbattle.domain.course.CourseId;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/course-battle-votes")
public class CourseBattleVoteController {

    private final CourseBattleVoteRepository courseBattleVoteRepository;

    private final CourseBattleRepository courseBattleRepository;

    @Autowired
    public CourseBattleVoteController(CourseBattleVoteRepository courseBattleVoteRepository, CourseBattleRepository courseBattleRepository) {
        this.courseBattleVoteRepository = courseBattleVoteRepository;
        this.courseBattleRepository = courseBattleRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void vote(@RequestBody @Valid CreateGuestCourseBattleVotingDto createGuestCourseBattleVotingDto) throws CourseBattleNotInProgressException {
        CourseBattle courseBattle = courseBattleRepository.findOne(new BattleId(createGuestCourseBattleVotingDto.getBattleId()));
        if (courseBattle.getState() != CourseBattleState.VOTING_IN_PROGRESS) {
            throw new CourseBattleNotInProgressException("Course-Battle Voting is not in Progress");
        }
        courseBattleVoteRepository.save(new CourseBattleVote(new BattleId(createGuestCourseBattleVotingDto.getBattleId()), new CourseId(createGuestCourseBattleVotingDto.getVotedCourseId())));
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @Secured({"ROLE_ADMIN"})
    public void clearCourseBattleVotes(@RequestParam String battleId) {
        List<CourseBattleVote> allCourseBattleVotesForBattle = courseBattleVoteRepository.findAllCourseBattleVotesForBattle(new BattleId(battleId));
        courseBattleVoteRepository.deleteInBatch(allCourseBattleVotesForBattle);
    }

    @RequestMapping(value = "search/countCourseVotes", method = RequestMethod.HEAD)
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    public Integer countVotes(@RequestParam String battleId, @RequestParam String courseId) {
        return courseBattleVoteRepository.countVotesForCourse(new CourseId(courseId), new BattleId(battleId));
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(CourseBattleNotInProgressException.class)
    public void handleCourseBattleNotInProgressException() {
        // Nothing to do
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

    public static class CourseBattleNotInProgressException extends Exception {
        public CourseBattleNotInProgressException(String s) {
            super(s);
        }
    }
}
