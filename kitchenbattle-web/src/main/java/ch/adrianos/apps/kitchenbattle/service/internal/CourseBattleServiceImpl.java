package ch.adrianos.apps.kitchenbattle.service.internal;

import ch.adrianos.apps.kitchenbattle.domain.coursebattle.*;
import ch.adrianos.apps.kitchenbattle.domain.course.Course;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseId;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseRepository;
import ch.adrianos.apps.kitchenbattle.service.CourseBattleService;
import ch.adrianos.apps.kitchenbattle.service.CourseBattleNotFoundException;
import ch.adrianos.apps.kitchenbattle.service.CourseNotFoundException;
import ch.adrianos.apps.kitchenbattle.service.CreateCourseBattleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = {CourseNotFoundException.class})
public class CourseBattleServiceImpl implements CourseBattleService {

    private final CourseBattleRepository courseBattleRepository;

    private final CourseRepository courseRepository;

    private final CourseBattleVoteRepository courseBattleVoteRepository;

    @Autowired
    public CourseBattleServiceImpl(CourseBattleRepository courseBattleRepository, CourseRepository courseRepository, CourseBattleVoteRepository courseBattleVoteRepository) {
        this.courseBattleRepository = courseBattleRepository;
        this.courseRepository = courseRepository;
        this.courseBattleVoteRepository = courseBattleVoteRepository;
    }

    @Override
    public String createNewCourseBattle(CreateCourseBattleDto createCourseBattleDto) throws CourseNotFoundException {
        Course courseOne = getCourse(createCourseBattleDto.getCourseOneId());
        Course courseTwo = getCourse(createCourseBattleDto.getCourseTwoId());
        if (!courseOne.getCourseType().equals(createCourseBattleDto.getCourseType())) {
            throw new IllegalArgumentException("CourseType of CourseOne does not match the expected Type of the CourseBattle");
        }
        if (!courseTwo.getCourseType().equals(createCourseBattleDto.getCourseType())) {
            throw new IllegalArgumentException("CourseType of CourseOne does not match the expected Type of the CourseBattle");
        }
        // TODO check that there is no existing battle between the two teams
        CourseBattle courseBattle = new CourseBattle(new BattleId(), createCourseBattleDto.getCourseType(), courseOne, courseTwo);
        CourseBattle savedCourseBattle = courseBattleRepository.save(courseBattle);
        return savedCourseBattle.getBattleId().getValue();
    }

    @Override
    public void updateCourseBattleStatus(String battleId, CourseBattleState newState) throws CourseBattleNotFoundException {
        CourseBattle battle = getCourseBattle(battleId);
        battle.setState(newState);
    }

    @Override
    public void deleteCourseBattle(String battleId) throws CourseBattleNotFoundException {
        CourseBattle battle = courseBattleRepository.findOne(new BattleId(battleId));
        if (battle == null) {
            throw new CourseBattleNotFoundException(battleId);
        }
        courseBattleRepository.delete(battle);
        List<CourseBattleVote> allCourseBattleVotesForBattle = courseBattleVoteRepository.findAllCourseBattleVotesForBattle(new BattleId(battleId));
        courseBattleVoteRepository.deleteInBatch(allCourseBattleVotesForBattle);
    }

    private CourseBattle getCourseBattle(String battleId) throws CourseBattleNotFoundException {
        CourseBattle battle = courseBattleRepository.findOne(new BattleId(battleId));
        if (battle == null) {
            throw new CourseBattleNotFoundException(battleId);
        }
        return battle;
    }

    private Course getCourse(String courseOneId) throws CourseNotFoundException {
        Course course = courseRepository.findOne(new CourseId(courseOneId));
        if (course == null) {
            throw new CourseNotFoundException(courseOneId);
        }
        return course;
    }
}
