package ch.adrianos.apps.kitchenbattle.web;

import ch.adrianos.apps.kitchenbattle.service.CourseBattleResultDto;
import ch.adrianos.apps.kitchenbattle.service.CourseBattleResultSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course-battle-result-search")
public class CourseBattleResultSearchController {

    private final CourseBattleResultSearchService courseBattleResultSearchService;

    @Autowired
    public CourseBattleResultSearchController(CourseBattleResultSearchService courseBattleResultSearchService) {
        this.courseBattleResultSearchService = courseBattleResultSearchService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CourseBattleResultDto getBattleVotingResult(@RequestParam String battleId) {
        return courseBattleResultSearchService.getBattleVotingResult(battleId);
    }
}
