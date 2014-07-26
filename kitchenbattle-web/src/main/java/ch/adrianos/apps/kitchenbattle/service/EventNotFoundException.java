package ch.adrianos.apps.kitchenbattle.service;

public class EventNotFoundException extends Exception {

    private final String eventId;

    public EventNotFoundException(String eventId) {
        this.eventId = eventId;
    }
}
