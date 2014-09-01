package ch.adrianos.apps.kitchenbattle.web;

import ch.adrianos.apps.kitchenbattle.domain.event.Event;
import ch.adrianos.apps.kitchenbattle.domain.event.EventId;
import ch.adrianos.apps.kitchenbattle.domain.event.EventRepository;
import ch.adrianos.apps.kitchenbattle.service.EventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/events")
public class EventController {

    private final EventRepository eventRepository;

    @Autowired
    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createEvent(@RequestBody Event event) {
        eventRepository.save(event);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Event> findEvents() {
        return eventRepository.findAll();
    }

    @RequestMapping(value = "/{eventId}", method = RequestMethod.GET)
    public Event getEvent(@PathVariable String eventId) throws EventNotFoundException {
        Event event = eventRepository.findOne(new EventId(eventId));
        if (event == null){
          throw new EventNotFoundException(eventId);
        }
        return event;
    }

    @RequestMapping(value = "/{eventId}", method = RequestMethod.DELETE)
    public void deleteEvent(@PathVariable String eventId) {
        eventRepository.delete(new EventId(eventId));
    }
}
