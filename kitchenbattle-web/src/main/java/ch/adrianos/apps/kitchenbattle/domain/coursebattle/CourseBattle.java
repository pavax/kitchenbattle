package ch.adrianos.apps.kitchenbattle.domain.coursebattle;

import ch.adrianos.apps.kitchenbattle.domain.course.Course;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseId;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseType;
import ch.adrianos.apps.kitchenbattle.domain.event.EventId;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class CourseBattle {

    @EmbeddedId
    @Valid
    @JsonUnwrapped
    private BattleId battleId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CourseType courseType;

    @NotNull
    private LocalDateTime createdAt;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "courseOneId")),
    })
    @NotNull
    @Valid
    @JsonUnwrapped(suffix = "One")
    private CourseId courseOneId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "courseTwoId")),
    })
    @NotNull
    @Valid
    @JsonUnwrapped(suffix = "Two")
    private CourseId courseTwoId;

    @Enumerated(EnumType.STRING)
    private CourseBattleState state;

    @Embedded
    @NotNull
    @JsonUnwrapped
    private EventId eventId;

    public CourseBattle(BattleId battleId, CourseType courseType, Course courseOne, Course courseTwo) {
        Assert.notNull(battleId);
        Assert.notNull(courseType);
        Assert.notNull(courseOne);
        Assert.notNull(courseTwo);
        Assert.isTrue(courseOne.getEventId().equals(courseTwo.getEventId()));
        this.battleId = battleId;
        this.courseType = courseType;
        this.courseOneId = courseOne.getCourseId();
        this.courseTwoId = courseTwo.getCourseId();
        this.state = CourseBattleState.INITIALIZED;
        this.eventId = courseOne.getEventId();
        this.createdAt = LocalDateTime.now();
    }

    protected CourseBattle() {
    }

    public BattleId getBattleId() {
        return battleId;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public CourseId getCourseOneId() {
        return courseOneId;
    }

    public CourseId getCourseTwoId() {
        return courseTwoId;
    }

    public CourseBattleState getState() {
        return state;
    }

    public void setState(CourseBattleState state) {
        this.state = state;
    }

    public EventId getEventId() {
        return eventId;
    }
}
