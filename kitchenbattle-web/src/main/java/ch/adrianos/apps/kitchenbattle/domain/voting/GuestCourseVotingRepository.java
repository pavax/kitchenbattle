package ch.adrianos.apps.kitchenbattle.domain.voting;

import ch.adrianos.apps.kitchenbattle.domain.course.CourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestCourseVotingRepository extends JpaRepository<GuestCourseVoting, String> {

    @Query("select distinct count (gvr) from GuestCourseVoting gvr where gvr.votedCourseId = :courseId ")
    Integer countVotesForCourse(@Param("courseId") CourseId courseId);
}
