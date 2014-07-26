package ch.adrianos.apps.kitchenbattle.service;

import ch.adrianos.apps.kitchenbattle.domain.event.EventId;

import java.util.List;

public interface CourseBattleSearchService {

    List<CourseBattleSearchResultDto> findAllCourseBattlesForEvent(EventId eventId);

    CourseBattleResultDto getCourseBattleResult(String battleId);

    List<CourseBattleResultDto> getAllCourseBattleResultsForEvent(EventId eventId);
}
