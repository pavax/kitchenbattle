package ch.adrianos.apps.kitchenbattle.service;

public interface CourseService {

    String createNewCourse(CreateCourseDto createCourseDto) throws TeamNotFoundException;

    void updateCourse(String courseId, UpdateCourseDto updateCourseDto) throws CourseNotFoundException;

    void deleteCourse(String courseId) throws CourseNotFoundException;
}
