package ch.adrianos.apps.kitchenbattle.service;

import java.util.List;

public interface CourseBattleResultSearchService {

    CourseBattleResultDto getCourseBattleVotingResult(String battleId);

    List<CourseBattleResultDto> getAllCourseBattleResults();
}
