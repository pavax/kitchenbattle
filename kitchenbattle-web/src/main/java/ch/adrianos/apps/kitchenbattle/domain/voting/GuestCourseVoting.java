package ch.adrianos.apps.kitchenbattle.domain.voting;

import ch.adrianos.apps.kitchenbattle.domain.course.CourseId;
import ch.adrianos.apps.kitchenbattle.domain.battle.BattleId;
import org.springframework.util.Assert;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class GuestCourseVoting {

    @Id
    private String id;

    @Embedded
    @Valid
    private BattleId courseBattleId;

    @Embedded
    @Valid
    private CourseId votedCourseId;

    private LocalDateTime createdAt;

    private String userName;

    public GuestCourseVoting(BattleId courseBattleId, CourseId votedCourseId) {
        Assert.notNull(courseBattleId);
        Assert.notNull(votedCourseId);
        this.id = UUID.randomUUID().toString();
        this.courseBattleId = courseBattleId;
        this.votedCourseId = votedCourseId;
        this.createdAt = LocalDateTime.now();
    }

    protected GuestCourseVoting() {
    }

    public String getId() {
        return id;
    }

    public BattleId getCourseBattleId() {
        return courseBattleId;
    }

    public CourseId getVotedCourseId() {
        return votedCourseId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getUserName() {
        return userName;
    }
}
