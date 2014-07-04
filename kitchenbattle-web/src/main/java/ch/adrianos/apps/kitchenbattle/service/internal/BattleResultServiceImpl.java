package ch.adrianos.apps.kitchenbattle.service.internal;

import ch.adrianos.apps.kitchenbattle.domain.team.Team;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamRepository;
import ch.adrianos.apps.kitchenbattle.service.BattleResultDto;
import ch.adrianos.apps.kitchenbattle.service.BattleResultService;

import java.util.List;

public class BattleResultServiceImpl implements BattleResultService {

    private final TeamRepository teamRepository;

    public BattleResultServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public BattleResultDto getBattleResult() {
        List<Team> teams = teamRepository.findAll();

        for (Team team : teams) {

        }


        return null;
    }
}
