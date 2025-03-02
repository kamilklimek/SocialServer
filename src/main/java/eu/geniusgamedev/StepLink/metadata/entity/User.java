package eu.geniusgamedev.StepLink.metadata.entity;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
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
@Getter
@NoArgsConstructor
@Builder
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;

    @OneToMany(mappedBy = "owner")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<EventInviteLink> eventInviteLinks = new LinkedList<>();

    @OneToMany(mappedBy = "follower")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<FollowersRelations> following = new LinkedList<>();

    @OneToMany(mappedBy = "followed")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<FollowersRelations> followed = new LinkedList<>();

    @OneToMany(mappedBy = "guest")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<AcceptedInvitation> acceptedInvitations = new LinkedList<>();

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "USERS_EVENTS",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="event_id")
    )
    private List<Event> joinedEvents = new LinkedList<>();

    @OneToMany(mappedBy = "owner")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Notification> notifications = new LinkedList<>();

    @Override
    public String toString() {
        return "Email: " + email + ", name: " + name + ", id: " + id;
    }
}
