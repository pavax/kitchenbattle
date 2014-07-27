package ch.adrianos.apps.kitchenbattle.domain.course;

import ch.adrianos.apps.kitchenbattle.domain.event.Event;
import ch.adrianos.apps.kitchenbattle.domain.event.EventId;
import ch.adrianos.apps.kitchenbattle.domain.team.Team;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamId;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {

    @EmbeddedId
    @JsonUnwrapped
    private CourseId courseId;

    @Embedded
    @JsonUnwrapped
    private TeamId teamId;

    @NotBlank
    @Length(max = 100)
    private String name;

    @NotBlank
    @Length(max = 250)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CourseType courseType;

    @ElementCollection
    private Set<CourseVariant> courseVariants = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "courseId")
    @Valid
    private Set<CourseImage> courseImages = new HashSet<>();

    @Embedded
    @NotNull
    @JsonUnwrapped
    private EventId eventId;

    public Course(CourseId courseId, String name, String description, Team teamId, CourseType courseType, Event event) {
        this.description = description;
        Assert.notNull(courseId);
        Assert.notNull(name);
        Assert.notNull(teamId);
        Assert.notNull(courseType);
        this.courseId = courseId;
        this.name = name;
        this.teamId = teamId.getTeamId();
        this.courseType = courseType;
        this.eventId = event.getId();
    }

    protected Course() {
    }


    public CourseId getCourseId() {
        return courseId;
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

    public void addImage(CourseVariant courseVariant, Image image) {
        removeImage(courseVariant);
        this.courseImages.add(new CourseImage(courseVariant, image));
        this.courseVariants.add(courseVariant);
    }

    public Image getImage(CourseVariant courseVariant) {
        Optional<CourseImage> courseImageOptional = FluentIterable.from(this.courseImages).firstMatch(new Predicate<CourseImage>() {
            @Override
            public boolean apply(CourseImage input) {
                return courseVariant.equals(input.getVariant());
            }
        });
        return courseImageOptional.isPresent() ? courseImageOptional.get().getImage() : null;
    }

    public void removeImage(CourseVariant courseVariant) {
        boolean hasBeenRemoved = Iterables.removeIf(this.courseImages, input -> input.getVariant().equals(courseVariant));
        if (hasBeenRemoved) {
            this.courseVariants.remove(courseVariant);
        }
    }

    public Set<CourseVariant> getCourseVariants() {
        return courseVariants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (courseId != null ? !courseId.equals(course.courseId) : course.courseId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return courseId != null ? courseId.hashCode() : 0;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public EventId getEventId() {
        return eventId;
    }
}
