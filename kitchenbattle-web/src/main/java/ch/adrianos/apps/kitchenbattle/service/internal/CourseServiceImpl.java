package ch.adrianos.apps.kitchenbattle.service.internal;

import ch.adrianos.apps.kitchenbattle.domain.course.Course;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseId;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseRepository;
import ch.adrianos.apps.kitchenbattle.domain.course.Image;
import ch.adrianos.apps.kitchenbattle.domain.team.Team;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamId;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamRepository;
import ch.adrianos.apps.kitchenbattle.service.*;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = {TeamNotFoundException.class, CourseNotFoundException.class})
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    private final TeamRepository teamRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, TeamRepository teamRepository) {
        this.courseRepository = courseRepository;
        this.teamRepository = teamRepository;
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
    public CourseDto getCourse(String courseId) throws CourseNotFoundException {
        Course course = courseRepository.findOne(new CourseId(courseId));
        if (course == null) {
            throw new CourseNotFoundException(courseId);
        }
        return toCourseDto(course);
    }

    @Override
    public void updateCourse(String courseId, CourseDto courseDto) throws CourseNotFoundException {
        Course course = courseRepository.findOne(new CourseId(courseId));
        if (course == null) {
            throw new CourseNotFoundException(courseId);
        }
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        // TODO?
    }

    @Override
    public void updateCourseImage(String courseId, byte[] image, String contentTyp) throws CourseNotFoundException {
        Course course = courseRepository.findOne(new CourseId(courseId));
        if (course == null) {
            throw new CourseNotFoundException(courseId);
        }
        course.setImage(new Image(image, contentTyp));
    }

    @Override
    public Image getCourseImage(String courseId) throws CourseNotFoundException {
        Course course = courseRepository.findOne(new CourseId(courseId));
        if (course == null) {
            throw new CourseNotFoundException(courseId);
        }


        return course.getImage();
    }

    @Override
    public void deleteCourse(String courseId) throws CourseNotFoundException {
        Course course = courseRepository.findOne(new CourseId(courseId));
        if (course == null) {
            throw new CourseNotFoundException(courseId);
        }
        courseRepository.delete(new CourseId(courseId));
    }

    @Override
    public List<CourseDto> findCoursesByTeam(String teamId) {
        List<Course> courseList = courseRepository.findByTeamId(new TeamId(teamId));
        return new ArrayList<>(Lists.transform(courseList, CourseServiceImpl::toCourseDto));
    }

    private static CourseDto toCourseDto(Course course) {
        return new CourseDto(course.getCourseId().getValue(), course.getName(), course.getTeamId().getValue(), course.getCourseType(), course.getDescription());
    }

    private static Course toCourse(CreateCourseDto createCourseDto, Team team) {
        return new Course(new CourseId(), createCourseDto.getCourseName(), createCourseDto.getDescription(), team, createCourseDto.getCourseType());
    }
}
