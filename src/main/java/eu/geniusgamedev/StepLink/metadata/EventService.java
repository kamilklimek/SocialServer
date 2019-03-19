package eu.geniusgamedev.StepLink.metadata;

import eu.geniusgamedev.StepLink.events.EventAssembler;
import eu.geniusgamedev.StepLink.events.EventInviteLinkAssembler;
import eu.geniusgamedev.StepLink.events.InviteLinkGenerator;
import eu.geniusgamedev.StepLink.events.models.EventCreateModel;
import eu.geniusgamedev.StepLink.events.models.EventInviteLinkModel;
import eu.geniusgamedev.StepLink.events.models.EventModel;
import eu.geniusgamedev.StepLink.metadata.entity.Event;
import eu.geniusgamedev.StepLink.metadata.entity.EventInviteLink;
import eu.geniusgamedev.StepLink.metadata.entity.User;
import eu.geniusgamedev.StepLink.metadata.repository.EventInviteLinkRepository;
import eu.geniusgamedev.StepLink.metadata.repository.EventRepository;
import eu.geniusgamedev.StepLink.metadata.search.EventSpecification;
import eu.geniusgamedev.StepLink.metadata.search.SpecificationFactory;
import eu.geniusgamedev.StepLink.metadata.search.SpecificationProxy;
import eu.geniusgamedev.StepLink.metadata.search.UserSpecification;
import eu.geniusgamedev.StepLink.security.authorization.UserIdentity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventAssembler eventAssembler;
    private final UserMetaDataService userMetaDataService;
    private final InviteLinkGenerator inviteLinkGenerator;
    private final EventInviteLinkRepository eventInviteLinkRepository;
    private final EventInviteLinkAssembler eventInviteLinkAssembler;
    private final SpecificationFactory specificationFactory;

    private Event getEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EventCouldNotBeFoundException(eventId));
    }

    public EventModel getEventModel(Long eventId) {
        log.info("Getting a event for id: {}", eventId);
        return eventAssembler.convertFromEntity(getEvent(eventId));
    }

    public EventModel addEvent(EventCreateModel eventModel) {
        log.info("Creating a new event: {}", eventModel);

        final Event event = eventAssembler.convertToEntity(eventModel);

        return eventAssembler.convertFromEntity(eventRepository.save(event));
    }

    @Transactional
    public EventModel joinEvent(UserIdentity userIdentity, Long eventId) {
        log.info("Joining to event with id: {}, by user: {}", eventId, userIdentity);
        validateEventExists(eventId);

        final Event event = getEvent(eventId);
        validateUserCanJoinEvent(event);

        final User user = userMetaDataService.findUser(userIdentity.getUserId());

        return eventAssembler.convertFromEntity(joinUserToEvent(user, event));
    }

    private Event joinUserToEvent(User user, Event event) {
        Set<Event> events = user.getJoinedEvents();
        events.add(event);
        return event;
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

        Set<Event> userEvents = user.getJoinedEvents();

        userEvents.removeIf(event -> event.getId().equals(eventId));
    }

    private void validateUserIsJoined(UserIdentity userIdentity, Long eventId) {
        boolean isJoined = eventRepository.existsByIdAndAttachedUsersId(eventId, userIdentity.getUserId());

        if (!isJoined) {
            throw new UserIsNotJoinedToEvent(userIdentity.getUserId(), eventId);
        }
    }

    public List<EventModel> getEvents(String eventSearch) {
        log.info("Get all events...");
        SpecificationProxy<Event> specification = specificationFactory.createProxy(new EventSpecification(eventSearch));

        return eventRepository.findAll(specification).stream()
                .map(eventAssembler::convertFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public EventInviteLinkModel inviteToEvent(UserIdentity userIdentity, Long eventId) {
        log.info("Creating invititation link to event: {} by user: {}", eventId, userIdentity);
        validateEventExists(eventId);

        final User user = userMetaDataService.findUser(userIdentity.getUserId());
        final Event event = getEvent(eventId);

        return eventInviteLinkAssembler.convertEntityToModel(createOrGetInviteLink(user, event));
    }

    private EventInviteLink createOrGetInviteLink(User user, Event event) {
        return eventInviteLinkRepository.findByOwnerIdAndEventId(user.getId(), event.getId())
                .orElseGet(() -> createInviteLink(user, event));
    }

    private EventInviteLink createInviteLink(User user, Event event) {
        String uniqueLink = inviteLinkGenerator.generateUniqueHash();

        EventInviteLink eventInviteLink = EventInviteLink.builder()
                .uniqueLink(uniqueLink)
                .owner(user)
                .event(event)
                .build();

        return eventInviteLinkRepository.save(eventInviteLink);
    }

    @Transactional
    public EventModel acceptInvitationToEvent(UserIdentity userIdentity, String uniqueHash) {
        log.info("Accepting invitation to event with hash: {} by user: {}", uniqueHash, uniqueHash);

        final EventInviteLink eventInviteLink = eventInviteLinkRepository.findByUniqueLink(uniqueHash)
                .orElseThrow(() -> new EventLinkCouldNotBeFoundException(uniqueHash));

        validateIsNotOwner(userIdentity, eventInviteLink);

        validateUserIsNotJoined(userIdentity, eventInviteLink.getEvent().getId());

        return joinEvent(userIdentity, eventInviteLink.getEvent().getId());
    }

    private void validateUserIsNotJoined(UserIdentity userIdentity, Long id) {
        boolean isJoined = eventRepository.existsByIdAndAttachedUsersId(id, userIdentity.getUserId());

        if (isJoined) {
            throw new UserAlreadyJoinedToEventException(userIdentity.getUserId(), id);
        }
    }

    private void validateIsNotOwner(UserIdentity userIdentity, EventInviteLink eventInviteLink) {
        boolean isOwner = eventInviteLink.getOwner().getId().equals(userIdentity.getUserId());

        if (isOwner) {
            throw new UserIsOwnerOfEventLinkException(userIdentity.getUserId(), eventInviteLink.getUniqueLink());
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
            super("User with id: " + userId + " is not assigned to event: " + eventId);
        }
    }

    private class EventLinkCouldNotBeFoundException extends RuntimeException {
        private EventLinkCouldNotBeFoundException(String uniqueHash) {
            super("Event link with unique hash: " + uniqueHash + " could not be found");
        }
    }

    public class UserIsOwnerOfEventLinkException extends RuntimeException{
        private UserIsOwnerOfEventLinkException(Long userId, String uniqueLink) {
            super("User: " + userId + " is owner of link: " + uniqueLink);
        }
    }

    public class UserAlreadyJoinedToEventException extends RuntimeException {
        private UserAlreadyJoinedToEventException(Long userId, Long id) {
            super("User with id: " + userId + " is already joined to id: " + id);
        }
    }
}
