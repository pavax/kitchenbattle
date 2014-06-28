package ch.adrianos.apps.kitchenbattle.domain.battle;

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
    private CourseId courseOne;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "courseTwoId")),
    })
    @NotNull
    @Valid
    private CourseId courseTwo;

    private boolean isBattleOpen;

    public CourseBattle(BattleId battleId, CourseType courseType, Course courseOne, Course courseTwo) {
        Assert.notNull(battleId);
        Assert.notNull(courseType);
        Assert.notNull(courseOne);
        Assert.notNull(courseTwo);
        this.battleId = battleId;
        this.courseType = courseType;
        this.courseOne = courseOne.getCourseId();
        this.courseTwo = courseTwo.getCourseId();
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

    public CourseId getCourseOne() {
        return courseOne;
    }

    public CourseId getCourseTwo() {
        return courseTwo;
    }

    public boolean isBattleOpen() {
        return isBattleOpen;
    }
}
