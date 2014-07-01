package ch.adrianos.apps.kitchenbattle.web;

import ch.adrianos.apps.kitchenbattle.domain.coursebattle.BattleId;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattle;
import ch.adrianos.apps.kitchenbattle.domain.coursebattle.CourseBattleRepository;
import ch.adrianos.apps.kitchenbattle.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/course-battles")
public class CourseBattleController {

    private final CourseBattleService courseBattleService;

    private final CourseBattleRepository courseBattleRepository;

    private final CourseBattleSearchService courseBattleSearchService;

    @Autowired
    public CourseBattleController(CourseBattleService courseBattleService, CourseBattleRepository courseBattleRepository, CourseBattleSearchService courseBattleSearchService) {
        this.courseBattleService = courseBattleService;
        this.courseBattleRepository = courseBattleRepository;
        this.courseBattleSearchService = courseBattleSearchService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String createNewBattle(@RequestBody @Valid CreateCourseBattleDto createCourseBattleDto) throws CourseNotFoundException {
        return courseBattleService.createNewCourseBattle(createCourseBattleDto);
    }

    @RequestMapping(value = "/{battleId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CourseBattle getBattle(@PathVariable String battleId) throws CourseBattleNotFoundException {
        CourseBattle courseBattle = courseBattleRepository.findOne(new BattleId(battleId));
        if (courseBattle == null) {
            throw new CourseBattleNotFoundException(battleId);
        }
        return courseBattle;
    }

    @RequestMapping(value = "{battleId}/status", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public void updateBattleStatus(@PathVariable String battleId, @RequestBody @Valid BattleStatusResource battleStatusResource) throws CourseBattleNotFoundException {
        courseBattleService.updateCourseBattleStatus(battleId, battleStatusResource.isBattleOpen());
    }

    @RequestMapping(value = "/search/allBattles", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CourseBattleSearchResultDto> searchAllCourseBattles() {
        return courseBattleSearchService.searchAllCourseBattles();
    }

    public static class BattleStatusResource {

        @NotNull
        private boolean isBattleOpen;

        public boolean isBattleOpen() {
            return isBattleOpen;
        }

        public void setBattleOpen(boolean battleOpen) {
            this.isBattleOpen = battleOpen;
        }
    }
}
