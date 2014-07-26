package ch.adrianos.apps.kitchenbattle.web;

import ch.adrianos.apps.kitchenbattle.domain.course.*;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamId;
import ch.adrianos.apps.kitchenbattle.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/courses")
public class CourseController {

    private final CourseService courseService;

    private final CourseRepository courseRepository;

    @Autowired
    public CourseController(CourseService courseService, CourseRepository courseRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String createCourse(@RequestBody @Valid CreateCourseDto createCourseDto) throws TeamNotFoundException, EventNotFoundException {
        return courseService.createNewCourse(createCourseDto);
    }

    @RequestMapping(value = "/find/byTeamId", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Course> findCoursesByTeamId(@RequestParam String teamId) {
        return courseRepository.findByTeamId(new TeamId(teamId));
    }

    @RequestMapping(value = "/{courseId}", method = RequestMethod.GET)
    public ResponseEntity<Course> getCourse(@PathVariable String courseId) throws CourseNotFoundException {
        Course course = getCourseInternally(courseId);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @RequestMapping(value = "/{courseId}", method = RequestMethod.PATCH, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void updateCourse(@PathVariable String courseId, @RequestBody @Valid UpdateCourseDto updateCourseDto) throws CourseNotFoundException {
        courseService.updateCourse(courseId, updateCourseDto);
    }

    @RequestMapping(value = "/{courseId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCourse(@PathVariable String courseId) throws CourseNotFoundException {
        courseService.deleteCourse(courseId);
    }

    @RequestMapping(value = "/{courseId}/image/{variant}", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.OK)
    public void updateCourseImage(@PathVariable String courseId, @PathVariable String variant, @RequestParam("file") MultipartFile file) throws IOException, CourseNotFoundException {
        if (!file.isEmpty()) {
            courseService.addImage(courseId, variant, new Image(file.getBytes(), file.getContentType()));
        }
    }

    @RequestMapping(value = "/{courseId}/image/{variant}", method = RequestMethod.GET)
    public ResponseEntity<?> getCourseImage(@PathVariable String courseId, @PathVariable String variant) throws CourseNotFoundException {
        Course course = getCourseInternally(courseId);
        Image courseImage = course.getImage(new CourseVariant(variant));
        if (courseImage == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(courseImage.getContentType()));
        return new ResponseEntity<>(courseImage.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{courseId}/image/{variant}", method = RequestMethod.DELETE)
    public void deleteCourseImage(@PathVariable String courseId, @PathVariable String variant) throws CourseNotFoundException {
        Course course = getCourseInternally(courseId);
        courseService.deleteImage(courseId, variant);
    }

    private Course getCourseInternally(String courseId) throws CourseNotFoundException {
        Course course = courseRepository.findOne(new CourseId(courseId));
        if (course == null) {
            throw new CourseNotFoundException(courseId);
        }
        return course;
    }
}
