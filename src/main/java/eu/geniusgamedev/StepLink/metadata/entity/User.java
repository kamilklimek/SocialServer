package eu.geniusgamedev.StepLink.metadata.entity;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="users")
@NoArgsConstructor
@Getter
@ToString(exclude = "password")
@Builder
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    private String email;
    private String password;

    @OneToMany(mappedBy = "to")
    private List<Follower> followers = new LinkedList<>();

    @OneToMany(mappedBy = "from")
    private List<Follower> following = new LinkedList<>();

    @ManyToMany
    @JoinTable(
            name = "USERS_EVENTS",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="event_id")
    )
    private List<Event> joinedEvents = new LinkedList<>();
}
