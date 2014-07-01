package ch.adrianos.apps.kitchenbattle.service.internal;

import ch.adrianos.apps.kitchenbattle.domain.battle.BattleId;
import ch.adrianos.apps.kitchenbattle.domain.battle.CourseBattle;
import ch.adrianos.apps.kitchenbattle.domain.battle.CourseBattleRepository;
import ch.adrianos.apps.kitchenbattle.domain.course.Course;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseId;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseRepository;
import ch.adrianos.apps.kitchenbattle.domain.team.Team;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamRepository;
import ch.adrianos.apps.kitchenbattle.domain.voting.CourseBattleVoteRepository;
import ch.adrianos.apps.kitchenbattle.service.CourseBattleResultDto;
import ch.adrianos.apps.kitchenbattle.service.CourseBattleResultSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseBattleResultSearchServiceImpl implements CourseBattleResultSearchService {

    private final CourseBattleRepository courseBattleRepository;

    private final TeamRepository teamRepository;

    private final CourseRepository courseRepository;

    private final CourseBattleVoteRepository courseBattleVoteRepository;

    @Autowired
    public CourseBattleResultSearchServiceImpl(CourseBattleRepository courseBattleRepository, TeamRepository teamRepository, CourseRepository courseRepository, CourseBattleVoteRepository courseBattleVoteRepository) {
        this.courseBattleRepository = courseBattleRepository;
        this.teamRepository = teamRepository;
        this.courseRepository = courseRepository;
        this.courseBattleVoteRepository = courseBattleVoteRepository;
    }

    @Override
    public CourseBattleResultDto getBattleVotingResult(String battleId) {
        CourseBattle courseBattle = courseBattleRepository.findOne(new BattleId(battleId));
        CourseBattleResultDto.CourseVotingResultDto courseOne = toBattleVotingCourseResultDto(courseBattle.getCourseOneId(),courseBattle.getBattleId());
        CourseBattleResultDto.CourseVotingResultDto courseTwo = toBattleVotingCourseResultDto(courseBattle.getCourseTwoId(), courseBattle.getBattleId());
        return toBattleVotingResultDto(courseBattle, courseOne, courseTwo);
    }

    private static CourseBattleResultDto toBattleVotingResultDto(CourseBattle courseBattle, CourseBattleResultDto.CourseVotingResultDto courseOne, CourseBattleResultDto.CourseVotingResultDto courseTwo) {
        return new CourseBattleResultDto(courseBattle.getBattleId().getValue(), courseBattle.isBattleOpen(), courseBattle.getCourseType(), courseOne, courseTwo);
    }

    private CourseBattleResultDto.CourseVotingResultDto toBattleVotingCourseResultDto(CourseId courseOneId, BattleId battleId) {
        Course course = courseRepository.findOne(courseOneId);
        Integer votesForCourse = courseBattleVoteRepository.countVotesForCourse(courseOneId, battleId);
        Team team = teamRepository.findOne(course.getTeamId());
        return new CourseBattleResultDto.CourseVotingResultDto(course.getName(), courseOneId.getValue(), team.getColor(), team.getName(), votesForCourse);
    }
}
