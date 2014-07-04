package ch.adrianos.apps.kitchenbattle.service;

import ch.adrianos.apps.kitchenbattle.domain.juryvote.JuryTeamVote;

import java.util.List;

public class TeamResultDto {

    private String teamName;

    private List<JuryTeamVote> juryTeamVotes;

    private int total;
}
