package ch.adrianos.apps.kitchenbattle.domain.coursebattle;

import ch.adrianos.apps.kitchenbattle.domain.course.Course;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseId;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseType;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class CourseBattle {

    @EmbeddedId
    @Valid
    private BattleId battleId;

    @NotNull
    private CourseType courseType;

    @NotNull
    private LocalDateTime createdAt;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "courseOneId")),
    })
    @NotNull
    @Valid
    private CourseId courseOneId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "courseTwoId")),
    })
    @NotNull
    @Valid
    private CourseId courseTwoId;

    private boolean isBattleOpen;

    public CourseBattle(BattleId battleId, CourseType courseType, Course courseOneId, Course courseTwoId) {
        Assert.notNull(battleId);
        Assert.notNull(courseType);
        Assert.notNull(courseOneId);
        Assert.notNull(courseTwoId);
        this.battleId = battleId;
        this.courseType = courseType;
        this.courseOneId = courseOneId.getCourseId();
        this.courseTwoId = courseTwoId.getCourseId();
        this.isBattleOpen = false;
        this.createdAt = LocalDateTime.now();
    }

    protected CourseBattle() {
    }

    public void setBattleOpen(boolean isBattleOpen) {
        this.isBattleOpen = isBattleOpen;
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

    public boolean isBattleOpen() {
        return isBattleOpen;
    }
}
