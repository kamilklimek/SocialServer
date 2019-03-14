package eu.geniusgamedev.StepLink.controllers;

import eu.geniusgamedev.StepLink.events.EventCreateModel;
import eu.geniusgamedev.StepLink.events.EventInviteLinkModel;
import eu.geniusgamedev.StepLink.events.EventModel;
import eu.geniusgamedev.StepLink.metadata.EventService;
import eu.geniusgamedev.StepLink.metadata.entity.Event;
import eu.geniusgamedev.StepLink.security.authorization.UserIdentity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventModel>> getEvents() {
        try {
            return ResponseEntity.ok(eventService.getEvents());
        } catch (Exception e) {
            log.error("Caught error while trying to get a events.");
            throw e;
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventModel> getEventById(@PathVariable(name = "id") Long eventId) {
        try {
            return ResponseEntity.ok(eventService.getEventModel(eventId));
        } catch(EventService.EventCouldNotBeFoundException e) {
            log.error("Entity event does not exists for id: {}.", eventId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e) {
            log.error("Caught error while trying to get a event by id: {}.", eventId);
            throw e;
        }
    }

    @PostMapping(value="", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> addEvent(@RequestBody EventCreateModel eventModel) {

        try {
            return ResponseEntity.ok(eventService.addEvent(eventModel));
        } catch (Exception e) {
            log.error("Error caught while trying to create a event: {}", eventModel);
            throw e;
        }
    }

    @PostMapping(value = "/join/{id}")
    public ResponseEntity<EventModel> joinEvent(@AuthenticationPrincipal UserIdentity userIdentity,
                                          @PathVariable(name = "id") Long eventId) {
        try {
            return ResponseEntity.ok(eventService.joinEvent(userIdentity, eventId));
        } catch (EventService.EventCouldNotBeFoundException e) {
            log.error("User: {} cannot join to not existing event: {}.", userIdentity, eventId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error caught while trying to join to event: {} as user: {}", eventId, userIdentity);
            throw e;
        }
    }

    @DeleteMapping(value = "/unjoin/{id}")
    public ResponseEntity<Void> unjoinEvent(@AuthenticationPrincipal UserIdentity userIdentity, @PathVariable(name = "id") Long eventId) {
        try {
            eventService.unjoinEvent(userIdentity, eventId);
            return ResponseEntity.ok().build();
        } catch (EventService.EventCouldNotBeFoundException e) {
            log.error("User: {} cannot unjoin from event which he belongs to: {}.", userIdentity, eventId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error caught while trying to unjoin event: {} as user: {}", eventId, userIdentity);
            throw e;
        }
    }
}
