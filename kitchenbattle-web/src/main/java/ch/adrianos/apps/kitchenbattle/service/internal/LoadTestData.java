package ch.adrianos.apps.kitchenbattle.service.internal;

import ch.adrianos.apps.kitchenbattle.domain.coursebattle.BattleId;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattle;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattleRepository;
import ch.adrianos.apps.kitchenbattle.domain.course.*;
import ch.adrianos.apps.kitchenbattle.domain.team.Team;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamId;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.IOException;
import java.io.InputStream;

@Component
@Profile("testdata")
public class LoadTestData implements ApplicationListener<ContextRefreshedEvent> {

    public static final TeamId TEAM_1 = new TeamId("Team1");
    public static final TeamId TEAM_2 = new TeamId("Team2");
    public static final CourseId COURSE_TEAM1_1 = new CourseId("Course-T1-1");
    public static final CourseId COURSE_TEAM2_1 = new CourseId("Course-T2-1");
    public static final CourseId COURSE_TEAM1_2 = new CourseId("Course-T1-2");
    public static final CourseId COURSE_TEAM2_2 = new CourseId("Course-T2-2");
    private final TeamRepository teamRepository;

    private final CourseRepository courseRepository;

    private final CourseBattleRepository courseBattleRepository;

    private final TransactionTemplate transactionTemplate;


    @Autowired
    public LoadTestData(PlatformTransactionManager platformTransactionManager, TeamRepository teamRepository, CourseRepository courseRepository, CourseBattleRepository courseBattleRepository) {
        this.teamRepository = teamRepository;
        this.courseRepository = courseRepository;
        this.courseBattleRepository = courseBattleRepository;
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                loadTeamTestData();
                loadCourseTestData();
                loadBattleTestData();
            }
        });
    }

    private void loadBattleTestData() {
        startBattle(COURSE_TEAM1_1, COURSE_TEAM2_1, new BattleId("Battle1"), CourseType.STARTER);
        startBattle(COURSE_TEAM2_2, COURSE_TEAM1_2, new BattleId("Battle2"), CourseType.MAIN);
    }

    private void startBattle(CourseId courseOneId, CourseId courseTwoId, BattleId battleId, CourseType courseType) {
        Course courseOne = courseRepository.findOne(courseOneId);
        Course courseTwo = courseRepository.findOne(courseTwoId);
        CourseBattle savedCourseBattle = courseBattleRepository.save(new CourseBattle(battleId, courseType, courseOne, courseTwo));
        savedCourseBattle.setBattleOpen(true);
    }

    private void loadTeamTestData() {
        teamRepository.save(new Team(TEAM_1, "Super-Team", "RED", "Blah blah 1"));
        teamRepository.save(new Team(TEAM_2, "Ultra-Team", "BLUE", "Blah blah 2"));
    }

    private void loadCourseTestData() {
        Course courseOne = courseRepository.save(new Course(COURSE_TEAM1_1, "Marinierte Oliven", "Feinste marinierte entsteinte Oliven mit frischen Kräutern und Feta-Käse", teamRepository.findOne(TEAM_1), CourseType.STARTER));
        setImage(courseOne, "oliven.png");
        Course courseTwo = courseRepository.save(new Course(COURSE_TEAM2_1, "Bärlauchsuppe", "Bärlauch schmeckt frisch und herzhaft nach Knoblauch und verbündet sich gerne mit Kartoffeln zu diesem cremigen Frühlings-Partysüppchen", teamRepository.findOne(TEAM_2), CourseType.STARTER));
        setImage(courseTwo, "suppe.jpg");

        Course courseThree = courseRepository.save(new Course(COURSE_TEAM1_2, "Marinierte Oliven (2)", "Feinste marinierte entsteinte Oliven mit frischen Kräutern und Feta-Käse", teamRepository.findOne(TEAM_1), CourseType.MAIN));
        setImage(courseThree, "oliven.png");
        Course courseFour = courseRepository.save(new Course(COURSE_TEAM2_2, "Bärlauchsuppe (2)", "Bärlauch schmeckt frisch und herzhaft nach Knoblauch und verbündet sich gerne mit Kartoffeln zu diesem cremigen Frühlings-Partysüppchen", teamRepository.findOne(TEAM_2), CourseType.MAIN));
        setImage(courseFour, "suppe.jpg");
    }

    private void setImage(Course course, String name) {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(name);
        try {
            byte[] bytes = IOUtils.toByteArray(in);
            course.addImage(new CourseVariant("NORMAL"), new Image(bytes, MediaType.IMAGE_GIF_VALUE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}