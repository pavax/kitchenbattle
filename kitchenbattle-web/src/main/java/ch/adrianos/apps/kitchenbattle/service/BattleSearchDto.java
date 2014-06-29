package ch.adrianos.apps.kitchenbattle.service;

import ch.adrianos.apps.kitchenbattle.domain.course.CourseType;

import java.time.LocalDateTime;

public class BattleSearchDto {

    private String battleId;

    private CourseType courseType;

    private LocalDateTime createdAt;

    private CourseSearchDto courseOne;

    private CourseSearchDto courseTwo;

    public BattleSearchDto(String battleId, CourseType courseType, LocalDateTime createdAt, CourseSearchDto courseOne, CourseSearchDto courseTwo) {
        this.battleId = battleId;
        this.courseType = courseType;
        this.createdAt = createdAt;
        this.courseOne = courseOne;
        this.courseTwo = courseTwo;
    }

    public String getBattleId() {
        return battleId;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public CourseSearchDto getCourseOne() {
        return courseOne;
    }

    public CourseSearchDto getCourseTwo() {
        return courseTwo;
    }
}
