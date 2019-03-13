package eu.geniusgamedev.StepLink.metadata;

import eu.geniusgamedev.StepLink.events.EventAssembler;
import eu.geniusgamedev.StepLink.events.EventCreateModel;
import eu.geniusgamedev.StepLink.metadata.entity.Event;
import eu.geniusgamedev.StepLink.metadata.entity.User;
import eu.geniusgamedev.StepLink.metadata.repository.EventRepository;
import eu.geniusgamedev.StepLink.security.authorization.UserIdentity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventAssembler eventAssembler;
    private final UserMetaDataService userMetaDataService;

    public Event getEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EventCouldNotBeFoundException(eventId));
    }

    public Event addEvent(EventCreateModel eventModel) {
        final Event event = eventAssembler.convertToEntity(eventModel);

        return eventRepository.save(event);
    }

    @Transactional
    public void joinEvent(UserIdentity userIdentity, Long eventId) {
        validateEventExists(eventId);

        final Event event = getEvent(eventId);
        validateUserCanJoinEvent(event);

        final User user = userMetaDataService.findUser(userIdentity.getUserId());

        joinUserToEvent(user, event);
    }

    private void joinUserToEvent(User user, Event event) {
        List<Event> events = user.getJoinedEvents();
        events.add(event);
    }

    private void validateUserCanJoinEvent(Event event) {

        if (event.getNumberOfParticipants() >= event.getMaxParticipants()) {
            throw new NoAvailableSpotInEvent(event.getId());
        }
    }

    private void validateEventExists(Long eventId) {
        final boolean exists = eventRepository.existsEventById(eventId);

        if (!exists) {
            throw new EventCouldNotBeFoundException(eventId);
        }
    }

    @Transactional
    public void unjoinEvent(UserIdentity userIdentity, Long eventId) {
        log.info("Remove user: {} from event: {}.", userIdentity, eventId);
        validateUserIsJoined(userIdentity, eventId);

        removeUserEvent(userIdentity, eventId);
    }

    private void removeUserEvent(UserIdentity userIdentity, Long eventId) {
        final User user = userMetaDataService.findUser(userIdentity.getUserId());

        List<Event> userEvents = user.getJoinedEvents();

        userEvents.removeIf(event -> event.getId().equals(eventId));
    }

    private void validateUserIsJoined(UserIdentity userIdentity, Long eventId) {
        boolean isJoined = eventRepository.existsByIdAndAttachedUsersId(eventId, userIdentity.getUserId());

        if (!isJoined) {
            throw new UserIsNotJoinedToEvent(userIdentity.getUserId(), eventId);
        }
    }

    public class EventCouldNotBeFoundException extends RuntimeException{
        EventCouldNotBeFoundException(Long eventId) {
            super("Event with id: " + eventId + " does not exists.");
        }
    }

    private class NoAvailableSpotInEvent extends RuntimeException {
        private NoAvailableSpotInEvent(Long eventId) {
            super("Event with id: " + eventId + " does not has any more spots");
        }
    }

    private class UserIsNotJoinedToEvent extends RuntimeException {
        private UserIsNotJoinedToEvent(Long userId, Long eventId) {
            super("User iwth id: " + userId + " is not assigned to event: " + eventId);
        }
    }
}
