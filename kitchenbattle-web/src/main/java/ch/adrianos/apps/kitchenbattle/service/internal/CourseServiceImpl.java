package ch.adrianos.apps.kitchenbattle.service.internal;

import ch.adrianos.apps.kitchenbattle.domain.course.*;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattle;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattleRepository;
import ch.adrianos.apps.kitchenbattle.domain.team.Team;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamId;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamRepository;
import ch.adrianos.apps.kitchenbattle.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = {TeamNotFoundException.class, CourseNotFoundException.class})
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    private final TeamRepository teamRepository;
    private CourseBattleRepository courseBattleRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, TeamRepository teamRepository, CourseBattleRepository courseBattleRepository) {
        this.courseRepository = courseRepository;
        this.teamRepository = teamRepository;
        this.courseBattleRepository = courseBattleRepository;
    }

    @Override
    public String createNewCourse(CreateCourseDto createCourseDto) throws TeamNotFoundException {
        String teamId = createCourseDto.getTeamId();
        Team team = teamRepository.findOne(new TeamId(teamId));
        if (team == null) {
            throw new TeamNotFoundException(teamId);
        }
        Course savedCourse = courseRepository.save(toCourse(createCourseDto, team));
        return savedCourse.getCourseId().getValue();
    }

    @Override
    public void updateCourse(String courseId, UpdateCourseDto updateCourseDto) throws CourseNotFoundException {
        Course course = courseRepository.findOne(new CourseId(courseId));
        if (course == null) {
            throw new CourseNotFoundException(courseId);
        }
        course.setName(updateCourseDto.getName());
        course.setDescription(updateCourseDto.getDescription());
        course.setCourseType(updateCourseDto.getCourseType());
    }

    @Override
    public void deleteCourse(String courseId) throws CourseNotFoundException {
        Course course = courseRepository.findOne(new CourseId(courseId));
        if (course == null) {
            throw new CourseNotFoundException(courseId);
        }
        List<CourseBattle> courseBattlesByCourseId = courseBattleRepository.findCourseBattlesByCourseId(course.getCourseId());
        courseBattleRepository.deleteInBatch(courseBattlesByCourseId);
        courseRepository.delete(course);
    }

    @Override
    public void addImage(String courseId, String courseVariant, Image image) throws CourseNotFoundException {
        Course course = courseRepository.findOne(new CourseId(courseId));
        if (course == null) {
            throw new CourseNotFoundException(courseId);
        }
        course.addImage(new CourseVariant(courseVariant), image);
    }

    @Override
    public void deleteImage(String courseId, String courseVariant) throws CourseNotFoundException {
        Course course = courseRepository.findOne(new CourseId(courseId));
        if (course == null) {
            throw new CourseNotFoundException(courseId);
        }
        course.removeImage(new CourseVariant(courseVariant));
    }

    private static Course toCourse(CreateCourseDto createCourseDto, Team team) {
        return new Course(new CourseId(), createCourseDto.getCourseName(), createCourseDto.getDescription(), team, createCourseDto.getCourseType());
    }
}
