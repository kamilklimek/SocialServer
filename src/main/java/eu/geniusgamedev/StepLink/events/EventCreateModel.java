package eu.geniusgamedev.StepLink.events;

import eu.geniusgamedev.StepLink.metadata.entity.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@Getter
@ToString
public class EventCreateModel {
    private String name;
    private String location;
    private Date date;
    private String description;
    private EventType type;
    private int maxParticipants;
}
