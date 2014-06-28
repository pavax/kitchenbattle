package ch.adrianos.apps.kitchenbattle.service;

public interface BattleService {

    String createNewBattle(CreateBattleDto createBattleDto) throws CourseNotFoundException;

    void updateBattleStatus(String battleId, boolean isOpen);
}
