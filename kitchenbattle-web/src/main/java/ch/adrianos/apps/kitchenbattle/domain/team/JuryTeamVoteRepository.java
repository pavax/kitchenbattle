package ch.adrianos.apps.kitchenbattle.domain.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JuryTeamVoteRepository extends JpaRepository<JuryTeamVote, String> {

    @Query("select distinct count (jtv) from JuryTeamVote jtv  where jtv.teamId = :teamId")
    Integer countJuryTeamVotesForTeam(@Param("teamId") TeamId teamId);
}
