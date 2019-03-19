package eu.geniusgamedev.StepLink.metadata.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "events_invite_links")
public class EventInviteLink {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "event")
    private Event event;

    @Column(unique = true)
    private String uniqueLink;

    @OneToMany(mappedBy = "eventInviteLink")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<AcceptedInvitation> acceptedInvitations = new HashSet<>();

    @Override
    public String toString() {
        return "EventInviteLinkObj = Event: " + event.getName() + ", uniqueLink: " + uniqueLink + ", owner: " + owner.getId();
    }
}
