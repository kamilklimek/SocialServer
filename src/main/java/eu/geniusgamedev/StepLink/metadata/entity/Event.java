package eu.geniusgamedev.StepLink.metadata.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private Date date;
    private String description;
    @Enumerated(EnumType.STRING)
    private EventType type;
    private int maxParticipants;
    @OneToMany(mappedBy = "event")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<EventInviteLink> eventInviteLink = new HashSet<>();

    @ManyToMany(mappedBy = "joinedEvents")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<User> attachedUsers = new HashSet<>();

    private Event(String name, String location, Date date, String description, EventType type, int maxParticipants) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.description = description;
        this.type = type;
        this.maxParticipants = maxParticipants;
    }

    @JsonIgnore
    public int getNumberOfParticipants() {
        return attachedUsers.size();
    }

    // TODO: Add field for picture, implements that like this: https://stackoverflow.com/questions/50363639/how-spring-boot-jpahibernate-saves-images

    public static class EventBuilder {
        private String name;
        private String location;
        private Date date;
        private String description;
        private EventType type;
        private int maxParticipants;

        public EventBuilder name(String name) {
            this.name = name;
            return this;
        }

        public EventBuilder location(String location) {
            this.location = location;
            return this;
        }

        public EventBuilder date(Date date) {
            this.date = date;
            return this;
        }

        public EventBuilder description(String desc) {
            this.description = desc;
            return this;
        }


        public EventBuilder type(EventType type) {
            this.type = type;
            return this;
        }


        public EventBuilder maxParticipants(int maxParticipants) {
            this.maxParticipants = maxParticipants;
            return this;
        }

        public Event build() {
            return new Event(name, location, date, description, type, maxParticipants);
        }

    }

}
