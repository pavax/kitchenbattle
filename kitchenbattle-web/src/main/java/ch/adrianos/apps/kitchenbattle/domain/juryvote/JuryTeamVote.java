package ch.adrianos.apps.kitchenbattle.domain.juryvote;

import ch.adrianos.apps.kitchenbattle.domain.team.TeamId;

import java.time.LocalDateTime;

public class JuryTeamVote {

    private TeamId teamId;

    private int votes;

    private String juryName;

    private LocalDateTime createdAt;
}
