package ch.adrianos.apps.kitchenbattle.service;

import ch.adrianos.apps.kitchenbattle.domain.course.CourseType;

import java.time.LocalDateTime;

public class BattleSearchDto {

    private String battleId;

    private CourseType courseType;

    private LocalDateTime createdAt;

    private CourseSearchDto courseOne;

    private CourseSearchDto courseTwo;

    private boolean isOpen;

    public BattleSearchDto(String battleId, boolean isOpen, CourseType courseType, LocalDateTime createdAt, CourseSearchDto courseOne, CourseSearchDto courseTwo) {
        this.battleId = battleId;
        this.isOpen = isOpen;
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

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
}
