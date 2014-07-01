package ch.adrianos.apps.kitchenbattle.service.internal;

import ch.adrianos.apps.kitchenbattle.domain.battle.BattleId;
import ch.adrianos.apps.kitchenbattle.domain.battle.CourseBattle;
import ch.adrianos.apps.kitchenbattle.domain.battle.CourseBattleRepository;
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

@Service
@Transactional
public class CourseBattleServiceImpl implements CourseBattleService {

    private final CourseBattleRepository courseBattleRepository;

    private final CourseRepository courseRepository;

    @Autowired
    public CourseBattleServiceImpl(CourseBattleRepository courseBattleRepository, CourseRepository courseRepository) {
        this.courseBattleRepository = courseBattleRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public String createNewCourseBattle(CreateCourseBattleDto createCourseBattleDto) throws CourseNotFoundException {
        Course courseOne = getCourse(createCourseBattleDto.getCourseOneId());
        Course courseTwo = getCourse(createCourseBattleDto.getCourseTwoId());
        // TODO check that there is no existing battle between the two teams
        CourseBattle courseBattle = new CourseBattle(new BattleId(), createCourseBattleDto.getCourseType(), courseOne, courseTwo);
        CourseBattle savedCourseBattle = courseBattleRepository.save(courseBattle);
        return savedCourseBattle.getBattleId().getValue();
    }

    @Override
    public void updateCourseBattleStatus(String battleId, boolean isOpen) throws CourseBattleNotFoundException {
        CourseBattle battle = getCourseBattle(battleId);
        battle.setBattleOpen(isOpen);
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
