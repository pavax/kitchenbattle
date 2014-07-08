package ch.adrianos.apps.kitchenbattle.service;

import ch.adrianos.apps.kitchenbattle.domain.course.CourseType;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattleState;

public class CourseBattleResultDto {

    private String battleId;

    private CourseType courseType;

    private CourseVotingResultDto courseOne;

    private CourseVotingResultDto courseTwo;

    private CourseBattleState state;

    private int totalGuestVotes;

    public CourseBattleResultDto(String battleId, CourseBattleState state, CourseType courseType, CourseVotingResultDto courseOne, CourseVotingResultDto courseTwo) {
        this.battleId = battleId;
        this.state = state;
        this.courseType = courseType;
        this.courseOne = courseOne;
        this.courseTwo = courseTwo;
        this.totalGuestVotes = courseOne.getTotalGuestVotes() + courseTwo.getTotalGuestVotes();
    }

    public CourseBattleState getState() {
        return state;
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


    public int getTotalGuestVotes() {
        return totalGuestVotes;
    }

    public static class CourseVotingResultDto {

        private String courseName;

        private String courseId;

        private String courseColor;

        private String teamId;

        private String teamName;

        private int totalGuestVotes;

        public CourseVotingResultDto(String courseName, String courseId, String courseColor, String teamId, String teamName, int totalGuestVotes) {
            this.courseName = courseName;
            this.courseId = courseId;
            this.courseColor = courseColor;
            this.teamId = teamId;
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

        public String getTeamId() {
            return teamId;
        }


    }
}
