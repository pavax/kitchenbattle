package ch.adrianos.apps.kitchenbattle.service;

import ch.adrianos.apps.kitchenbattle.domain.course.Image;

public interface CourseService {

    String createNewCourse(CreateCourseDto createCourseDto) throws TeamNotFoundException, EventNotFoundException;

    void updateCourse(String courseId, UpdateCourseDto updateCourseDto) throws CourseNotFoundException;

    void deleteCourse(String courseId) throws CourseNotFoundException;

    void addImage(String courseId, String courseVariant, Image image) throws CourseNotFoundException;

    void deleteImage(String courseId, String courseVariant) throws CourseNotFoundException;
}
