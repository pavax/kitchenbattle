package ch.adrianos.apps.kitchenbattle.service;

import ch.adrianos.apps.kitchenbattle.domain.course.Image;

import java.util.List;

public interface CourseService {

    String createNewCourse(CreateCourseDto createCourseDto) throws TeamNotFoundException;

    CourseDto getCourse(String courseId) throws CourseNotFoundException;

    void updateCourse(String courseId, CourseDto courseDto) throws CourseNotFoundException;

    void updateCourseImage(String courseId, byte[] image, String contentTyp) throws CourseNotFoundException;

    Image getCourseImage(String courseId) throws CourseNotFoundException;

    void deleteCourse(String courseId) throws CourseNotFoundException;

    List<CourseDto> findCoursesByTeam(String teamId);
}
