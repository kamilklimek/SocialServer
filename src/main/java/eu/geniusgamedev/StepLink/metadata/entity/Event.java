package eu.geniusgamedev.StepLink.metadata.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
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

    @ManyToMany(mappedBy = "joinedEvents")
    private List<User> attachedUsers = new LinkedList<>();

    // TODO: Add field for picture, implements that like this: https://stackoverflow.com/questions/50363639/how-spring-boot-jpahibernate-saves-images
}
