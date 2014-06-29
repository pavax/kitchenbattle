package ch.adrianos.apps.kitchenbattle.web;

import ch.adrianos.apps.kitchenbattle.domain.battle.CourseBattle;
import ch.adrianos.apps.kitchenbattle.domain.battle.CourseBattleRepository;
import ch.adrianos.apps.kitchenbattle.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/battles")
public class BattleController {

    private final BattleService battleService;

    private final CourseBattleRepository courseBattleRepository;

    private final BattleSearchService battleSearchService;

    @Autowired
    public BattleController(BattleService battleService, CourseBattleRepository courseBattleRepository, BattleSearchService battleSearchService) {
        this.battleService = battleService;
        this.courseBattleRepository = courseBattleRepository;
        this.battleSearchService = battleSearchService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String createNewBattle(@RequestBody @Valid CreateBattleDto createBattleDto) throws CourseNotFoundException {
        return battleService.createNewBattle(createBattleDto);
    }


    @RequestMapping(value = "{battleId}/status", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public void updateBattleStatus(@PathVariable String battleId, @RequestBody BattleStatusResource battleStatusResource) {
        battleService.updateBattleStatus(battleId, battleStatusResource.isBattleOpen());
    }

    @RequestMapping(value = "/search/allBattles", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<BattleSearchDto> findAllCourseBattles() {
        return battleSearchService.findAllBattles();
    }

    public static class BattleStatusResource {

        private boolean isBattleOpen;

        public boolean isBattleOpen() {
            return isBattleOpen;
        }

        public void setBattleOpen(boolean battleOpen) {
            this.isBattleOpen = battleOpen;
        }
    }
}
