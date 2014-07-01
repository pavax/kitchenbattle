package ch.adrianos.apps.kitchenbattle.service;

import ch.adrianos.apps.kitchenbattle.domain.course.CourseType;

public class UpdateCourseDto {

    private String name;

    private CourseType courseType;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
