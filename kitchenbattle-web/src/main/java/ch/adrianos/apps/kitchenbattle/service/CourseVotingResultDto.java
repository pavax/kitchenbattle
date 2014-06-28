package ch.adrianos.apps.kitchenbattle.service;

public class CourseVotingResultDto {

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
