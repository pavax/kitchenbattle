package ch.adrianos.apps.kitchenbattle.service;

import ch.adrianos.apps.kitchenbattle.domain.course.CourseType;

import java.time.LocalDateTime;

public class CourseBattleSearchResultDto {

    private String battleId;

    private CourseType courseType;

    private LocalDateTime createdAt;

    private DetailedCourseDto courseOne;

    private DetailedCourseDto courseTwo;

    private boolean isOpen;

    public CourseBattleSearchResultDto(String battleId, boolean isOpen, CourseType courseType, LocalDateTime createdAt, DetailedCourseDto courseOne, DetailedCourseDto courseTwo) {
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

    public DetailedCourseDto getCourseOne() {
        return courseOne;
    }

    public DetailedCourseDto getCourseTwo() {
        return courseTwo;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public static class DetailedCourseDto {

        private String teamName;

        private String teamId;

        private String courseName;

        private String courseId;

        public DetailedCourseDto(String teamName, String teamId, String courseName, String courseId) {
            this.teamName = teamName;
            this.teamId = teamId;
            this.courseName = courseName;
            this.courseId = courseId;
        }

        public String getTeamName() {
            return teamName;
        }

        public String getTeamId() {
            return teamId;
        }

        public String getCourseName() {
            return courseName;
        }

        public String getCourseId() {
            return courseId;
        }
    }
}
