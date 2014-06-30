package ch.adrianos.apps.kitchenbattle.service;

import ch.adrianos.apps.kitchenbattle.domain.course.CourseType;

public class CourseDto {

    private String courseId;

    private String name;

    private String teamId;

    private CourseType courseType;

    private String description;

    public CourseDto(String courseId, String name, String teamId, CourseType courseType, String description) {
        this.courseId = courseId;
        this.name = name;
        this.teamId = teamId;
        this.courseType = courseType;
        this.description = description;
    }

    public CourseDto() {
    }

    public String getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public String getTeamId() {
        return teamId;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
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
