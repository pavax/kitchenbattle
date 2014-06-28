package ch.adrianos.apps.kitchenbattle.service.internal;

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
import scala.tools.nsc.Global;

import java.io.IOException;
import java.io.InputStream;

@Component
@Profile("testdata")
public class LoadTestData implements ApplicationListener<ContextRefreshedEvent> {

    public static final TeamId TEAM_1 = new TeamId("Team1");
    public static final TeamId TEAM_2 = new TeamId("Team2");
    public static final CourseId COURSE_TEAM1_1 = new CourseId("Course-T1-1");
    public static final CourseId COURSE_TEAM2_1 = new CourseId("Course-T2-1");
    private final TeamRepository teamRepository;

    private final CourseRepository courseRepository;

    private final TransactionTemplate transactionTemplate;

    @Autowired
    public LoadTestData(PlatformTransactionManager platformTransactionManager, TeamRepository teamRepository, CourseRepository courseRepository) {
        this.teamRepository = teamRepository;
        this.courseRepository = courseRepository;
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                loadTeamTestData();
                loadCourseTestData();
            }
        });
    }

    private void loadTeamTestData() {
        teamRepository.save(new Team(TEAM_1, "Super-Team", "RED", "Blah blah 1"));
        teamRepository.save(new Team(TEAM_2, "Ultra-Team", "BLUE", "Blah blah 2"));
    }

    private void loadCourseTestData() {
        Course courseOne = courseRepository.save(new Course(COURSE_TEAM1_1, "Delicious Starter-Course of Team 1", teamRepository.findOne(TEAM_1), CourseType.STARTER));
        setImage(courseOne);
        Course courseTwo = courseRepository.save(new Course(COURSE_TEAM2_1, "Delicious Starter-Course of Team 2", teamRepository.findOne(TEAM_2), CourseType.STARTER));
        setImage(courseTwo);
    }

    private void setImage(Course course) {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("Desert.gif");
        try {
            byte[] bytes = IOUtils.toByteArray(in);
            course.setImage(new Image(bytes, MediaType.IMAGE_GIF_VALUE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}