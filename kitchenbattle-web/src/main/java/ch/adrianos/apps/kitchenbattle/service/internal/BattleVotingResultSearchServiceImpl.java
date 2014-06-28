package ch.adrianos.apps.kitchenbattle.service.internal;

import ch.adrianos.apps.kitchenbattle.domain.battle.BattleId;
import ch.adrianos.apps.kitchenbattle.domain.battle.CourseBattle;
import ch.adrianos.apps.kitchenbattle.domain.battle.CourseBattleRepository;
import ch.adrianos.apps.kitchenbattle.domain.course.Course;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseId;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseRepository;
import ch.adrianos.apps.kitchenbattle.domain.team.Team;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamRepository;
import ch.adrianos.apps.kitchenbattle.domain.voting.GuestCourseVotingRepository;
import ch.adrianos.apps.kitchenbattle.service.CourseVotingResultDto;
import ch.adrianos.apps.kitchenbattle.service.BattleVotingResultDto;
import ch.adrianos.apps.kitchenbattle.service.BattleVotingResultSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BattleVotingResultSearchServiceImpl implements BattleVotingResultSearchService {

    private final CourseBattleRepository courseBattleRepository;

    private final TeamRepository teamRepository;

    private final CourseRepository courseRepository;

    private final GuestCourseVotingRepository guestCourseVotingRepository;

    @Autowired
    public BattleVotingResultSearchServiceImpl(CourseBattleRepository courseBattleRepository, TeamRepository teamRepository, CourseRepository courseRepository, GuestCourseVotingRepository guestCourseVotingRepository) {
        this.courseBattleRepository = courseBattleRepository;
        this.teamRepository = teamRepository;
        this.courseRepository = courseRepository;
        this.guestCourseVotingRepository = guestCourseVotingRepository;
    }

    @Override
    public BattleVotingResultDto getBattleVotingResult(String battleId) {
        CourseBattle courseBattle = courseBattleRepository.findOne(new BattleId(battleId));
        CourseVotingResultDto courseOne = toBattleVotingCourseResultDto(courseBattle.getCourseOne());
        CourseVotingResultDto courseTwo = toBattleVotingCourseResultDto(courseBattle.getCourseTwo());
        return toBattleVotingResultDto(courseBattle, courseOne, courseTwo);
    }

    private static BattleVotingResultDto toBattleVotingResultDto(CourseBattle courseBattle, CourseVotingResultDto courseOne, CourseVotingResultDto courseTwo) {
        return new BattleVotingResultDto(courseBattle.getBattleId().getValue(), courseBattle.isBattleOpen(), courseBattle.getCourseType(), courseOne, courseTwo);
    }

    private CourseVotingResultDto toBattleVotingCourseResultDto(CourseId courseOneId) {
        Course course = courseRepository.findOne(courseOneId);
        Integer votesForCourse = guestCourseVotingRepository.countVotesForCourse(courseOneId);
        Team team = teamRepository.findOne(course.getTeamId());
        return new CourseVotingResultDto(course.getName(), courseOneId.getValue(), team.getColor(), team.getName(), votesForCourse);
    }
}
