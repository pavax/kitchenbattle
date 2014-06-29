package ch.adrianos.apps.kitchenbattle.service;

public class CourseSearchDto {

        private String teamName;

        private String teamId;

        private String courseName;

        private String courseId;

        public CourseSearchDto(String teamName, String teamId, String courseName, String courseId) {
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