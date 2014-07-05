package ch.adrianos.apps.kitchenbattle.domain.team;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class JuryTeamVote {

    @Id
    private String id;

    private TeamId teamId;

    private int votes;

    private String juryName;

    private LocalDateTime createdAt;

    public JuryTeamVote(int votes, String juryName, TeamId teamId1) {
        this.id = UUID.randomUUID().toString();
        this.teamId = teamId1;
        this.votes = votes;
        this.juryName = juryName;
        this.createdAt = LocalDateTime.now();
    }

    protected JuryTeamVote() {
    }

    public String getId() {
        return id;
    }

    public TeamId getTeamId() {
        return teamId;
    }

    public int getVotes() {
        return votes;
    }

    public String getJuryName() {
        return juryName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
