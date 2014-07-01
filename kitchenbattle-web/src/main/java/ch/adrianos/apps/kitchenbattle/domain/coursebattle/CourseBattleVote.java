package ch.adrianos.apps.kitchenbattle.domain.coursebattle;

import ch.adrianos.apps.kitchenbattle.domain.course.CourseId;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.BattleId;
import org.springframework.util.Assert;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class CourseBattleVote {

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

    public CourseBattleVote(BattleId courseBattleId, CourseId votedCourseId) {
        Assert.notNull(courseBattleId);
        Assert.notNull(votedCourseId);
        this.id = UUID.randomUUID().toString();
        this.courseBattleId = courseBattleId;
        this.votedCourseId = votedCourseId;
        this.createdAt = LocalDateTime.now();
    }

    protected CourseBattleVote() {
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
