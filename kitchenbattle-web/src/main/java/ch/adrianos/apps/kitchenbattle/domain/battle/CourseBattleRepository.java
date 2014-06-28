package ch.adrianos.apps.kitchenbattle.domain.battle;

import ch.adrianos.apps.kitchenbattle.domain.team.TeamId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseBattleRepository extends JpaRepository<CourseBattle, BattleId> {

}
