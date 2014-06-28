package ch.adrianos.apps.kitchenbattle.domain.course;

import ch.adrianos.apps.kitchenbattle.domain.team.TeamId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, CourseId> {

    @Query("select c from Course c where c.teamId = :teamId")
    public List<Course> findByTeamId(@Param("teamId") TeamId teamId);
}
