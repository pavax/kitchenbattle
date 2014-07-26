package ch.adrianos.apps.kitchenbattle.service.internal;

import ch.adrianos.apps.kitchenbattle.domain.course.CourseId;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.BattleId;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattle;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattleRepository;
import ch.adrianos.apps.kitchenbattle.domain.course.Course;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseRepository;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattleVoteRepository;
import ch.adrianos.apps.kitchenbattle.domain.event.EventId;
import ch.adrianos.apps.kitchenbattle.domain.team.Team;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamRepository;
import ch.adrianos.apps.kitchenbattle.service.CourseBattleResultDto;
import ch.adrianos.apps.kitchenbattle.service.CourseBattleSearchResultDto;
import ch.adrianos.apps.kitchenbattle.service.CourseBattleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CourseBattleSearchServiceImpl implements CourseBattleSearchService {

    private final CourseBattleRepository courseBattleRepository;

    private final CourseRepository courseRepository;

    private final TeamRepository teamRepository;

    private final CourseBattleVoteRepository courseBattleVoteRepository;

    @Autowired
    public CourseBattleSearchServiceImpl(CourseBattleRepository courseBattleRepository, CourseRepository courseRepository, TeamRepository teamRepository, CourseBattleVoteRepository courseBattleVoteRepository) {
        this.courseBattleRepository = courseBattleRepository;
        this.courseRepository = courseRepository;
        this.teamRepository = teamRepository;
        this.courseBattleVoteRepository = courseBattleVoteRepository;
    }

    @Override
    public List<CourseBattleSearchResultDto> findAllCourseBattlesForEvent(EventId eventId) {
        List<CourseBattle> courseBattles = courseBattleRepository.findAllByEventId(eventId);
        List<CourseBattleSearchResultDto> courseBattleSearchResultDtos = new ArrayList<>();
        for (CourseBattle courseBattle : courseBattles) {
            courseBattleSearchResultDtos.add(new CourseBattleSearchResultDto(courseBattle.getBattleId().getValue(), courseBattle.getState(), courseBattle.getCourseType(), courseBattle.getCreatedAt(), toDetailedCourseDto(courseRepository.findOne(courseBattle.getCourseOneId()), teamRepository.findOne(courseRepository.findOne(courseBattle.getCourseOneId()).getTeamId())), toDetailedCourseDto(courseRepository.findOne(courseBattle.getCourseTwoId()), teamRepository.findOne(courseRepository.findOne(courseBattle.getCourseTwoId()).getTeamId()))));
        }
        return courseBattleSearchResultDtos;
    }

    @Override
    public CourseBattleResultDto getCourseBattleResult(String battleId) {
        CourseBattle courseBattle = courseBattleRepository.findOne(new BattleId(battleId));
        CourseBattleResultDto.CourseVotingResultDto courseOne = toBattleVotingCourseResultDto(courseBattle.getCourseOneId(), courseBattle.getBattleId());
        CourseBattleResultDto.CourseVotingResultDto courseTwo = toBattleVotingCourseResultDto(courseBattle.getCourseTwoId(), courseBattle.getBattleId());
        return toBattleVotingResultDto(courseBattle, courseOne, courseTwo);
    }

    @Override
    public List<CourseBattleResultDto> getAllCourseBattleResultsForEvent(EventId eventId) {
        List<CourseBattle> courseBattles = courseBattleRepository.findAllByEventId(eventId);
        List<CourseBattleResultDto> result = new ArrayList<>();
        for (CourseBattle courseBattle : courseBattles) {
            result.add(getCourseBattleResult(courseBattle.getBattleId().getValue()));
        }
        return result;
    }

    private static CourseBattleResultDto toBattleVotingResultDto(CourseBattle courseBattle, CourseBattleResultDto.CourseVotingResultDto courseOne, CourseBattleResultDto.CourseVotingResultDto courseTwo) {
        return new CourseBattleResultDto(courseBattle.getBattleId().getValue(), courseBattle.getState(), courseBattle.getCourseType(), courseOne, courseTwo);
    }

    private CourseBattleResultDto.CourseVotingResultDto toBattleVotingCourseResultDto(CourseId courseOneId, BattleId battleId) {
        Course course = courseRepository.findOne(courseOneId);
        Integer votesForCourse = courseBattleVoteRepository.countVotesForCourse(courseOneId, battleId);
        Team team = teamRepository.findOne(course.getTeamId());
        return new CourseBattleResultDto.CourseVotingResultDto(course.getName(), courseOneId.getValue(), team.getColor(), team.getTeamId().getValue(), team.getName(), votesForCourse);
    }

    private CourseBattleSearchResultDto.DetailedCourseDto toDetailedCourseDto(Course course, Team team) {
        return new CourseBattleSearchResultDto.DetailedCourseDto(team.getName(), team.getTeamId().getValue(), course.getName(), course.getCourseId().getValue());
    }
}
