package ch.adrianos.apps.kitchenbattle.service;

import ch.adrianos.apps.kitchenbattle.domain.course.CourseType;

public class CreateBattleDto {

    private String courseOneId;

    private String courseTwoId;

    private CourseType courseType;

    public String getCourseOneId() {
        return courseOneId;
    }

    public void setCourseOneId(String courseOneId) {
        this.courseOneId = courseOneId;
    }

    public String getCourseTwoId() {
        return courseTwoId;
    }

    public void setCourseTwoId(String courseTwoId) {
        this.courseTwoId = courseTwoId;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
}
