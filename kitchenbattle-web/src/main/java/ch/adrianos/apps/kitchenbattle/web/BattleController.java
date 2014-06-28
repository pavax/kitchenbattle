package ch.adrianos.apps.kitchenbattle.web;

import ch.adrianos.apps.kitchenbattle.service.BattleService;
import ch.adrianos.apps.kitchenbattle.service.CourseNotFoundException;
import ch.adrianos.apps.kitchenbattle.service.CreateBattleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/battles")
public class BattleController {

    private final BattleService battleService;

    @Autowired
    public BattleController(BattleService battleService) {
        this.battleService = battleService;
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
