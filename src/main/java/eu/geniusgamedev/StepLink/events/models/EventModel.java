package eu.geniusgamedev.StepLink.events.models;

import eu.geniusgamedev.StepLink.metadata.entity.EventType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Builder
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventModel {
    private Long id;
    private String name;
    private String location;
    private Date date;
    private String description;
    private EventType type;
    private int maxParticipants;
    private int numberOfParticipants;
}
