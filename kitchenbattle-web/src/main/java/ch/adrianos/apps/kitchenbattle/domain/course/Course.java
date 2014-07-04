package ch.adrianos.apps.kitchenbattle.domain.course;

import ch.adrianos.apps.kitchenbattle.domain.team.Team;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamId;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Course {

    @EmbeddedId
    private CourseId courseId;

    @NotBlank
    @Length(max = 100)
    private String name;

    @NotBlank
    @Length(max = 250)
    private String description;

    @Embedded
    private TeamId teamId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CourseType courseType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Valid
    private Image image;

    public Course(CourseId courseId, String name, String description, Team teamId, CourseType courseType) {
        this.description = description;
        Assert.notNull(courseId);
        Assert.notNull(name);
        Assert.notNull(teamId);
        Assert.notNull(courseType);
        this.courseId = courseId;
        this.name = name;
        this.teamId = teamId.getTeamId();
        this.courseType = courseType;
    }

    protected Course() {
    }


    public CourseId getCourseId() {
        return courseId;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public TeamId getTeamId() {
        return teamId;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
