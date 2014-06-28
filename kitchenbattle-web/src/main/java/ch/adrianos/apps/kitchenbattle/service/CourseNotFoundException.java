package ch.adrianos.apps.kitchenbattle.service;

public class CourseNotFoundException extends Exception {
    private final String courseId;

    public CourseNotFoundException(String courseId) {
        this.courseId = courseId;
    }
}
