package ch.adrianos.apps.kitchenbattle.service;

import java.util.List;

public interface CourseBattleResultSearchService {

    CourseBattleResultDto getBattleVotingResult(String battleId);

    List<CourseBattleResultDto> getAllCourseBattleResults();
}
