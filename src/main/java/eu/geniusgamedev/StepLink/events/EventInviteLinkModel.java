package eu.geniusgamedev.StepLink.events;

import eu.geniusgamedev.StepLink.profile.ProfileModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventInviteLinkModel {
    private ProfileModel inviter;
    private EventModel event;
    private String uniqueLink;
}
