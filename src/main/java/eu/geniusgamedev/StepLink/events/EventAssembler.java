package eu.geniusgamedev.StepLink.events;

import eu.geniusgamedev.StepLink.metadata.entity.Event;
import org.springframework.stereotype.Component;

@Component
public class EventAssembler {
    public Event convertToEntity(EventCreateModel eventCreateModel) {
        return new Event.EventBuilder()
                .date(eventCreateModel.getDate())
                .description(eventCreateModel.getDescription())
                .location(eventCreateModel.getLocation())
                .maxParticipants(eventCreateModel.getMaxParticipants())
                .name(eventCreateModel.getName())
                .type(eventCreateModel.getType())
                .build();
    }

    public EventModel convertFromEntity(Event event) {
        return EventModel.builder()
                .id(event.getId())
                .name(event.getName())
                .date(event.getDate())
                .location(event.getLocation())
                .description(event.getDescription())
                .type(event.getType())
                .maxParticipants(event.getMaxParticipants())
                .numberOfParticipants(event.getNumberOfParticipants())
                .build();

    }
}
