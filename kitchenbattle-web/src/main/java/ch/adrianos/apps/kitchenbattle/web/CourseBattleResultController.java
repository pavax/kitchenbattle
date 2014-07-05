package ch.adrianos.apps.kitchenbattle.web;

import ch.adrianos.apps.kitchenbattle.service.CourseBattleResultDto;
import ch.adrianos.apps.kitchenbattle.service.CourseBattleResultSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course-battle-results")
public class CourseBattleResultController {

    private final CourseBattleResultSearchService courseBattleResultSearchService;

    @Autowired
    public CourseBattleResultController(CourseBattleResultSearchService courseBattleResultSearchService) {
        this.courseBattleResultSearchService = courseBattleResultSearchService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CourseBattleResultDto> getAllCourseBattleResults() {
        return courseBattleResultSearchService.getAllCourseBattleResults();
    }

    @RequestMapping(value = "/{battleId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CourseBattleResultDto getBattleVotingResult(@PathVariable String battleId) {
        return courseBattleResultSearchService.getCourseBattleVotingResult(battleId);
    }
}
