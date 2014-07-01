package ch.adrianos.apps.kitchenbattle.service;

import ch.adrianos.apps.kitchenbattle.domain.course.CourseType;

public class CourseBattleResultDto {

    private String battleId;

    private CourseType courseType;

    private CourseVotingResultDto courseOne;

    private CourseVotingResultDto courseTwo;

    private boolean isBattleOpen;

    private int totalGuestVotes;

    public CourseBattleResultDto(String battleId, boolean isBattleOpen, CourseType courseType, CourseVotingResultDto courseOne, CourseVotingResultDto courseTwo) {
        this.battleId = battleId;
        this.isBattleOpen = isBattleOpen;
        this.courseType = courseType;
        this.courseOne = courseOne;
        this.courseTwo = courseTwo;
        this.totalGuestVotes = courseOne.getTotalGuestVotes() + courseTwo.getTotalGuestVotes();
    }

    public String getBattleId() {
        return battleId;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public CourseVotingResultDto getCourseOne() {
        return courseOne;
    }

    public CourseVotingResultDto getCourseTwo() {
        return courseTwo;
    }

    public boolean isBattleOpen() {
        return isBattleOpen;
    }

    public int getTotalGuestVotes() {
        return totalGuestVotes;
    }

    public static class CourseVotingResultDto {

        private String courseName;

        private String courseId;

        private String courseColor;

        private String teamName;

        private int totalGuestVotes;

        public CourseVotingResultDto(String courseName, String courseId, String courseColor, String teamName, int totalGuestVotes) {
            this.courseName = courseName;
            this.courseId = courseId;
            this.courseColor = courseColor;
            this.teamName = teamName;
            this.totalGuestVotes = totalGuestVotes;
        }

        public String getCourseName() {
            return courseName;
        }

        public String getCourseId() {
            return courseId;
        }

        public String getCourseColor() {
            return courseColor;
        }

        public String getTeamName() {
            return teamName;
        }

        public int getTotalGuestVotes() {
            return totalGuestVotes;
        }
    }
}
