package ch.adrianos.apps.kitchenbattle.service.internal;

import ch.adrianos.apps.kitchenbattle.domain.battle.BattleId;
import ch.adrianos.apps.kitchenbattle.domain.battle.CourseBattle;
import ch.adrianos.apps.kitchenbattle.domain.battle.CourseBattleRepository;
import ch.adrianos.apps.kitchenbattle.domain.course.Course;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseId;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseRepository;
import ch.adrianos.apps.kitchenbattle.service.BattleService;
import ch.adrianos.apps.kitchenbattle.service.CourseNotFoundException;
import ch.adrianos.apps.kitchenbattle.service.CreateBattleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BattleServiceImpl implements BattleService {

    private final CourseBattleRepository courseBattleRepository;

    private final CourseRepository courseRepository;

    @Autowired
    public BattleServiceImpl(CourseBattleRepository courseBattleRepository, CourseRepository courseRepository) {
        this.courseBattleRepository = courseBattleRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public String createNewBattle(CreateBattleDto createBattleDto) throws CourseNotFoundException {
        Course courseOne = getCourse(createBattleDto.getCourseOneId());
        Course courseTwo = getCourse(createBattleDto.getCourseTwoId());
        // TODO check that there is no existing battle between the two teams
        CourseBattle courseBattle = new CourseBattle(new BattleId(), createBattleDto.getCourseType(), courseOne, courseTwo);
        CourseBattle savedCourseBattle = courseBattleRepository.save(courseBattle);
        return savedCourseBattle.getBattleId().getValue();
    }

    @Override
    public void updateBattleStatus(String battleId, boolean isOpen) {
        CourseBattle battle = courseBattleRepository.findOne(new BattleId(battleId));
        battle.setBattleOpen(isOpen);
    }

    private Course getCourse(String courseOneId) throws CourseNotFoundException {
        Course course = courseRepository.findOne(new CourseId(courseOneId));
        if (course == null) {
            throw new CourseNotFoundException(courseOneId);
        }
        return course;
    }
}
