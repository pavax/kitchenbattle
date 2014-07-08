package ch.adrianos.apps.kitchenbattle.service;

import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattleState;

public interface CourseBattleService {

    String createNewCourseBattle(CreateCourseBattleDto createCourseBattleDto) throws CourseNotFoundException;

    void updateCourseBattleStatus(String battleId, CourseBattleState newState) throws CourseBattleNotFoundException;

    void deleteCourseBattle(String battleId) throws CourseBattleNotFoundException;
}
