package ch.adrianos.apps.kitchenbattle.web;

import ch.adrianos.apps.kitchenbattle.domain.course.Image;
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

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String createCourse(@RequestBody @Valid CreateCourseDto createCourseDto) throws TeamNotFoundException {
        String courseId = courseService.createNewCourse(createCourseDto);
        return courseId;
    }

    @RequestMapping(value = "/find/byTeamId", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CourseDto> findCoursesByTeamId(@RequestParam String teamId) {
        return courseService.findCoursesByTeam(teamId);
    }

    @RequestMapping(value = "/{courseId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CourseDto getCourse(@PathVariable String courseId) throws TeamNotFoundException, CourseNotFoundException {
        CourseDto course = courseService.getCourse(courseId);
        return course;
    }

    @RequestMapping(value = "/{courseId}", method = RequestMethod.PATCH, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void updateCourse(@PathVariable String courseId, CourseDto courseDto) throws TeamNotFoundException, CourseNotFoundException {
        courseDto.setCourseId(courseId);
        courseService.updateCourse(courseId, courseDto);
    }

    @RequestMapping(value = "/{courseId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCourse(@PathVariable String courseId) throws TeamNotFoundException, CourseNotFoundException {
        courseService.deleteCourse(courseId);
    }

    @RequestMapping(value = "/{courseId}/image", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.OK)
    public void updateCourseImage(@PathVariable String courseId, @RequestParam("file") MultipartFile file) throws CourseNotFoundException, IOException {
        if (!file.isEmpty()) {
            String contentType = file.getContentType();
            MediaType mediaType = MediaType.parseMediaType(contentType);
            if (!mediaType.equals(MediaType.IMAGE_JPEG) && !mediaType.equals(MediaType.IMAGE_PNG) && !mediaType.equals(MediaType.IMAGE_GIF)) {
                throw new IllegalArgumentException("Image Type not supported: " + contentType);
            }
            courseService.updateCourseImage(courseId, file.getBytes(), contentType);
        }
    }

    @RequestMapping(value = "/{courseId}/image", method = RequestMethod.GET)
    public ResponseEntity<?> getCourseImage(@PathVariable String courseId) throws CourseNotFoundException {
        Image courseImage = courseService.getCourseImage(courseId);
        if (courseImage == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(courseImage.getContentType()));
        return new ResponseEntity<>(courseImage.getContent(), headers, HttpStatus.OK);
    }
}
