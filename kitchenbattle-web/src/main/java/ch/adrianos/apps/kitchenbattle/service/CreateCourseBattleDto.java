package ch.adrianos.apps.kitchenbattle.service;

import ch.adrianos.apps.kitchenbattle.domain.course.CourseType;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class CreateCourseBattleDto {

    @NotBlank
    private String courseOneId;

    @NotBlank
    private String courseTwoId;

    @NotNull
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
