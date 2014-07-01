package ch.adrianos.apps.kitchenbattle.service;

public interface CourseBattleService {

    String createNewCourseBattle(CreateCourseBattleDto createCourseBattleDto) throws CourseNotFoundException;

    void updateCourseBattleStatus(String battleId, boolean isOpen) throws CourseBattleNotFoundException;
}
