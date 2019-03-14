package eu.geniusgamedev.StepLink.metadata.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@Entity
@Builder
@Getter
@ToString
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
    private List<AcceptedInvitation> acceptedInvitations = new LinkedList<>();
}
