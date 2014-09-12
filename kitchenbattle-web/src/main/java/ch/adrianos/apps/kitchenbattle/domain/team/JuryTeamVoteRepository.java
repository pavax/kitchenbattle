package ch.adrianos.apps.kitchenbattle.domain.team;

import ch.adrianos.apps.kitchenbattle.domain.event.EventId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JuryTeamVoteRepository extends JpaRepository<JuryTeamVote, String> {

    @Query("select distinct jtv from JuryTeamVote jtv, Team team where team.teamId = jtv.teamId and team.eventId = :eventId")
    List<JuryTeamVote> findAllJuryTeamVotesForEvent(@Param("eventId") EventId eventId);

    @Query("select distinct count (jtv) from JuryTeamVote jtv  where jtv.teamId = :teamId")
    Integer countJuryTeamVotesForTeam(@Param("teamId") TeamId teamId);
}
