package ch.adrianos.apps.kitchenbattle.service.internal;

import ch.adrianos.apps.kitchenbattle.domain.course.Course;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseRepository;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattle;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattleRepository;
import ch.adrianos.apps.kitchenbattle.domain.team.Team;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamId;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamRepository;
import ch.adrianos.apps.kitchenbattle.service.CreateTeamDto;
import ch.adrianos.apps.kitchenbattle.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final CourseRepository courseRepository;

    private final CourseBattleRepository courseBattleRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, CourseRepository courseRepository, CourseBattleRepository courseBattleRepository) {
        this.teamRepository = teamRepository;
        this.courseRepository = courseRepository;
        this.courseBattleRepository = courseBattleRepository;
    }

    @Override
    public String createTeam(CreateTeamDto createTeamDto) {
        Team save = teamRepository.save(new Team(new TeamId(), createTeamDto.getName(), createTeamDto.getColor(), createTeamDto.getDescription()));
        return save.getTeamId().getValue();
    }

    @Override
    public void deleteTeam(String teamId) {
        List<Course> courses = courseRepository.findByTeamId(new TeamId(teamId));
        deleteRelatedCourseBattles(courses);
        courseRepository.deleteInBatch(courses);
        teamRepository.delete(new TeamId(teamId));

    }

    private void deleteRelatedCourseBattles(List<Course> courses) {
        for (Course course : courses) {
            List<CourseBattle> courseBattlesByCourseId = courseBattleRepository.findCourseBattlesByCourseId(course.getCourseId());
            courseBattleRepository.deleteInBatch(courseBattlesByCourseId);
        }
    }
}
