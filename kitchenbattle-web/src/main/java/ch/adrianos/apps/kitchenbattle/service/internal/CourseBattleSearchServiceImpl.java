package ch.adrianos.apps.kitchenbattle.service.internal;

import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattle;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattleRepository;
import ch.adrianos.apps.kitchenbattle.domain.course.Course;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseRepository;
import ch.adrianos.apps.kitchenbattle.domain.team.Team;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamRepository;
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

    @Autowired
    public CourseBattleSearchServiceImpl(CourseBattleRepository courseBattleRepository, CourseRepository courseRepository, TeamRepository teamRepository) {
        this.courseBattleRepository = courseBattleRepository;
        this.courseRepository = courseRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public List<CourseBattleSearchResultDto> searchAllCourseBattles() {
        List<CourseBattle> courseBattles = courseBattleRepository.findAll();
        List<CourseBattleSearchResultDto> courseBattleSearchResultDtos = new ArrayList<>();
        for (CourseBattle courseBattle : courseBattles) {
            courseBattleSearchResultDtos.add(new CourseBattleSearchResultDto(courseBattle.getBattleId().getValue(), courseBattle.getState(), courseBattle.getCourseType(), courseBattle.getCreatedAt(), toDetailedCourseDto(courseRepository.findOne(courseBattle.getCourseOneId()), teamRepository.findOne(courseRepository.findOne(courseBattle.getCourseOneId()).getTeamId())), toDetailedCourseDto(courseRepository.findOne(courseBattle.getCourseTwoId()), teamRepository.findOne(courseRepository.findOne(courseBattle.getCourseTwoId()).getTeamId()))));
        }
        return courseBattleSearchResultDtos;
    }

    private CourseBattleSearchResultDto.DetailedCourseDto toDetailedCourseDto(Course course, Team team) {
        return new CourseBattleSearchResultDto.DetailedCourseDto(team.getName(), team.getTeamId().getValue(), course.getName(), course.getCourseId().getValue());
    }
}
