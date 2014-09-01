package ch.adrianos.apps.kitchenbattle.web;

import ch.adrianos.apps.kitchenbattle.domain.coursebattle.BattleId;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattle;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattleRepository;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattleState;
import ch.adrianos.apps.kitchenbattle.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/course-battles")
public class CourseBattleController {

    private final CourseBattleService courseBattleService;

    private final CourseBattleRepository courseBattleRepository;

    @Autowired
    public CourseBattleController(CourseBattleService courseBattleService, CourseBattleRepository courseBattleRepository) {
        this.courseBattleService = courseBattleService;
        this.courseBattleRepository = courseBattleRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_ADMIN"})
    public String createNewBattle(@RequestBody @Valid CreateCourseBattleDto createCourseBattleDto) throws CourseNotFoundException {
        return courseBattleService.createNewCourseBattle(createCourseBattleDto);

    }

    @RequestMapping(value = "/{battleId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CourseBattle getBattle(@PathVariable String battleId) throws CourseBattleNotFoundException {
        System.out.println("000009999");
        CourseBattle courseBattle = courseBattleRepository.findOne(new BattleId(battleId));
        if (courseBattle == null) {
            throw new CourseBattleNotFoundException(battleId);
        }
        return courseBattle;
    }

    @RequestMapping(value = "/{battleId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    public void deleteBattle(@PathVariable String battleId) throws CourseBattleNotFoundException {
        courseBattleService.deleteCourseBattle(battleId);
    }

    @RequestMapping(value = "{battleId}/state", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    public void updateBattleStatus(@PathVariable String battleId, @RequestBody CourseBattleStateResource courseBattleStateResource) throws CourseBattleNotFoundException {
        courseBattleService.updateCourseBattleStatus(battleId, courseBattleStateResource.nextState);
    }

    public static final class CourseBattleStateResource {

        private CourseBattleState nextState;

        public CourseBattleState getNextState() {
            return nextState;
        }

        public void setNextState(CourseBattleState nextState) {
            this.nextState = nextState;
        }
    }
}
