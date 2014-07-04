package ch.adrianos.apps.kitchenbattle.service.internal;

import ch.adrianos.apps.kitchenbattle.domain.coursebattle.BattleId;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattle;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattleRepository;
import ch.adrianos.apps.kitchenbattle.domain.course.Course;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseId;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseRepository;
import ch.adrianos.apps.kitchenbattle.domain.team.Team;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamRepository;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattleVoteRepository;
import ch.adrianos.apps.kitchenbattle.service.CourseBattleResultDto;
import ch.adrianos.apps.kitchenbattle.service.CourseBattleResultSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        CourseBattleResultDto.CourseVotingResultDto courseOne = toBattleVotingCourseResultDto(courseBattle.getCourseOneId(), courseBattle.getBattleId());
        CourseBattleResultDto.CourseVotingResultDto courseTwo = toBattleVotingCourseResultDto(courseBattle.getCourseTwoId(), courseBattle.getBattleId());
        return toBattleVotingResultDto(courseBattle, courseOne, courseTwo);
    }

    @Override
    public List<CourseBattleResultDto> getAllCourseBattleResults() {
        List<CourseBattle> courseBattles = courseBattleRepository.findAll();
        List<CourseBattleResultDto> result = new ArrayList<>();
        for (CourseBattle courseBattle : courseBattles) {
            result.add(getBattleVotingResult(courseBattle.getBattleId().getValue()));
        }
        return result;
    }

    private static CourseBattleResultDto toBattleVotingResultDto(CourseBattle courseBattle, CourseBattleResultDto.CourseVotingResultDto courseOne, CourseBattleResultDto.CourseVotingResultDto courseTwo) {
        return new CourseBattleResultDto(courseBattle.getBattleId().getValue(), courseBattle.isBattleOpen(), courseBattle.getCourseType(), courseOne, courseTwo);
    }

    private CourseBattleResultDto.CourseVotingResultDto toBattleVotingCourseResultDto(CourseId courseOneId, BattleId battleId) {
        Course course = courseRepository.findOne(courseOneId);
        Integer votesForCourse = courseBattleVoteRepository.countVotesForCourse(courseOneId, battleId);
        Team team = teamRepository.findOne(course.getTeamId());
        return new CourseBattleResultDto.CourseVotingResultDto(course.getName(), courseOneId.getValue(), team.getColor(), team.getTeamId().getValue(), team.getName(), votesForCourse);
    }
}
