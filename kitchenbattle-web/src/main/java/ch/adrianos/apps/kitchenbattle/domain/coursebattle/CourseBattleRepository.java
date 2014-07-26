package ch.adrianos.apps.kitchenbattle.domain.coursebattle;

import ch.adrianos.apps.kitchenbattle.domain.course.CourseId;
import ch.adrianos.apps.kitchenbattle.domain.event.EventId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseBattleRepository extends JpaRepository<CourseBattle, BattleId> {

    @Query("select cb from CourseBattle cb where cb.courseOneId = :courseId or cb.courseTwoId = :courseId")
    List<CourseBattle> findCourseBattlesByCourseId(@Param("courseId") CourseId courseId);

    @Query("select cb from CourseBattle cb where cb.eventId = :eventId")
    List<CourseBattle> findAllByEventId(@Param("eventId") EventId eventId);

}
