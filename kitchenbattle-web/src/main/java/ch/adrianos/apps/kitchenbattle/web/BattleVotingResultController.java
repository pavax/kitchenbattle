package ch.adrianos.apps.kitchenbattle.web;

import ch.adrianos.apps.kitchenbattle.service.BattleVotingResultDto;
import ch.adrianos.apps.kitchenbattle.service.BattleVotingResultSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/voting-results")
public class BattleVotingResultController {

    private final BattleVotingResultSearchService battleVotingResultSearchService;

    @Autowired
    public BattleVotingResultController(BattleVotingResultSearchService battleVotingResultSearchService) {
        this.battleVotingResultSearchService = battleVotingResultSearchService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public BattleVotingResultDto getBattleVotingResult(@RequestParam String battleId) {
        return battleVotingResultSearchService.getBattleVotingResult(battleId);
    }
}
