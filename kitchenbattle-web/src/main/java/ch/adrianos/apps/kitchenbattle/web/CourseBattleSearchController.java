package ch.adrianos.apps.kitchenbattle.web;

import ch.adrianos.apps.kitchenbattle.domain.event.EventId;
import ch.adrianos.apps.kitchenbattle.service.CourseBattleResultDto;
import ch.adrianos.apps.kitchenbattle.service.CourseBattleSearchResultDto;
import ch.adrianos.apps.kitchenbattle.service.CourseBattleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course-battles-search")
public class CourseBattleSearchController {

    private final CourseBattleSearchService courseBattleSearchService;

    @Autowired
    public CourseBattleSearchController(CourseBattleSearchService courseBattleSearchService) {
        this.courseBattleSearchService = courseBattleSearchService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CourseBattleSearchResultDto> findAllCourseBattlesForEvent(@RequestParam String eventId) {
        return courseBattleSearchService.findAllCourseBattlesForEvent(new EventId(eventId));
    }

    @RequestMapping(value = "/results", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CourseBattleResultDto> getAllCourseBattleResultsForEvent(@RequestParam String eventId) {
        return courseBattleSearchService.getAllCourseBattleResultsForEvent(new EventId(eventId));
    }

    @RequestMapping(value = "/results/{battleId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CourseBattleResultDto getCourseBattleResult(@PathVariable String battleId) {
        return courseBattleSearchService.getCourseBattleResult(battleId);
    }

}
