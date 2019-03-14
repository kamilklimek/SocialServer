package eu.geniusgamedev.StepLink.metadata.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "event_accepted_invitation")
@NoArgsConstructor
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AcceptedInvitation {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_invite_link")
    private EventInviteLink eventInviteLink;

    @ManyToOne
    @JoinColumn(name = "guest")
    private User guest;
}
