package ch.adrianos.apps.kitchenbattle.domain.team;

import ch.adrianos.apps.kitchenbattle.domain.event.EventId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, TeamId> {

    @Query("select t from Team t where t.eventId = :eventId")
    List<Team> findAllTeamsForEvent(@Param("eventId") EventId eventId);
}
