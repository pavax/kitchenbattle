package ch.adrianos.apps.kitchenbattle.service;

import ch.adrianos.apps.kitchenbattle.domain.course.CourseType;

public class BattleVotingResultDto {

    private String battleId;

    private CourseType courseType;

    private CourseVotingResultDto courseOne;

    private CourseVotingResultDto courseTwo;

    private boolean isBattleOpen;

    private int totalGuestVotes;

    public BattleVotingResultDto(String battleId, boolean isBattleOpen, CourseType courseType, CourseVotingResultDto courseOne, CourseVotingResultDto courseTwo) {
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
}
