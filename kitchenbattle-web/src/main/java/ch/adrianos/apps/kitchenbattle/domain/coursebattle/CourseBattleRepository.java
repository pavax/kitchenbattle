package ch.adrianos.apps.kitchenbattle.domain.coursebattle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseBattleRepository extends JpaRepository<CourseBattle, BattleId> {

}
