package eu.geniusgamedev.StepLink.controllers;

import eu.geniusgamedev.StepLink.events.models.EventInviteLinkModel;
import eu.geniusgamedev.StepLink.metadata.EventService;
import eu.geniusgamedev.StepLink.security.authorization.UserIdentity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventInvitationController {
    private final EventService eventService;


    @PutMapping(value = "/invite/{id}")
    public ResponseEntity<EventInviteLinkModel> getInviteLink(@PathVariable(name = "id") Long eventId,
                                                              @AuthenticationPrincipal UserIdentity userIdentity) {

        try {
            return ResponseEntity.ok(eventService.inviteToEvent(userIdentity, eventId));
        }  catch (Exception e) {
            log.error("Caught error while trying to generate invite link");
            throw e;
        }
    }

    @PostMapping(value = "/invitation/{uniqueHash}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> acceptInvitationToEvent(@PathVariable String uniqueHash,
                                                              @AuthenticationPrincipal UserIdentity userIdentity) {

        try {
            return ResponseEntity.ok(eventService.acceptInvitationToEvent(userIdentity, uniqueHash));
        } catch (EventService.UserIsOwnerOfEventLinkException e) {
            log.error("User: {} is owner of invitation and cannot accept it: {}", userIdentity, uniqueHash);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("You cannot accept invitation due to you are owner of this.");
        } catch (EventService.UserAlreadyJoinedToEventException e) {
            log.error("User: {} has already joined to event: {}", userIdentity, uniqueHash);
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("You cannot accept invitation due to you have already joined.");
        }
    }

}
