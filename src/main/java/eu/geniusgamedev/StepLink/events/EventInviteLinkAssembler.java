package eu.geniusgamedev.StepLink.events;

import eu.geniusgamedev.StepLink.metadata.entity.EventInviteLink;
import eu.geniusgamedev.StepLink.profile.ProfileModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventInviteLinkAssembler {
    private final EventAssembler eventAssembler;
    private final ProfileModelAssembler profileModelAssembler;
    private final InviteLinkGenerator inviteLinkGenerator;

    public EventInviteLinkModel convertEntityToModel(EventInviteLink eventInviteLink) {
        return EventInviteLinkModel.builder()
                .event(eventAssembler.convertFromEntity(eventInviteLink.getEvent()))
                .inviter(profileModelAssembler.convertEntityToProfileModel(eventInviteLink.getOwner()))
                .uniqueLink(inviteLinkGenerator.appendHostToLink(eventInviteLink.getUniqueLink()))
                .build();
    }
}
