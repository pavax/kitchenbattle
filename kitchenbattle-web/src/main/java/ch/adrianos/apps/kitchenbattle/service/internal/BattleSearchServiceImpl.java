package ch.adrianos.apps.kitchenbattle.service.internal;

import ch.adrianos.apps.kitchenbattle.domain.battle.CourseBattle;
import ch.adrianos.apps.kitchenbattle.domain.battle.CourseBattleRepository;
import ch.adrianos.apps.kitchenbattle.domain.course.Course;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseId;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseRepository;
import ch.adrianos.apps.kitchenbattle.domain.team.Team;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamRepository;
import ch.adrianos.apps.kitchenbattle.service.BattleSearchDto;
import ch.adrianos.apps.kitchenbattle.service.BattleSearchService;
import ch.adrianos.apps.kitchenbattle.service.CourseSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BattleSearchServiceImpl implements BattleSearchService {

    private final CourseBattleRepository courseBattleRepository;

    private final CourseRepository courseRepository;

    private final TeamRepository teamRepository;

    @Autowired
    public BattleSearchServiceImpl(CourseBattleRepository courseBattleRepository, CourseRepository courseRepository, TeamRepository teamRepository) {
        this.courseBattleRepository = courseBattleRepository;
        this.courseRepository = courseRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public List<BattleSearchDto> findAllBattles() {
        List<CourseBattle> courseBattles = courseBattleRepository.findAll();
        List<BattleSearchDto> battleSearchDtos = new ArrayList<>();
        for (CourseBattle courseBattle : courseBattles) {
            battleSearchDtos.add(new BattleSearchDto(courseBattle.getBattleId().getValue(),courseBattle.getCourseType(), courseBattle.getCreatedAt(), toCourseSearchDto(courseBattle.getCourseOneId()), toCourseSearchDto(courseBattle.getCourseTwoId())));
        }
        return battleSearchDtos;
    }

    private CourseSearchDto toCourseSearchDto(CourseId courseId) {
        Course course = courseRepository.findOne(courseId);
        Team team = teamRepository.findOne(course.getTeamId());
        return new CourseSearchDto(team.getName(),team.getTeamId().getValue(),course.getName(),course.getCourseId().getValue());
    }
}
