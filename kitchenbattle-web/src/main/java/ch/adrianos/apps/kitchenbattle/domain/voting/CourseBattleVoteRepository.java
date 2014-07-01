package ch.adrianos.apps.kitchenbattle.domain.voting;

import ch.adrianos.apps.kitchenbattle.domain.battle.BattleId;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseBattleVoteRepository extends JpaRepository<CourseBattleVote, String> {

    @Query("select distinct count (cbv) from CourseBattleVote cbv where cbv.votedCourseId = :courseId and cbv.courseBattleId = :battleId ")
    Integer countVotesForCourse(@Param("courseId") CourseId courseId, @Param("battleId") BattleId battleId);
}
