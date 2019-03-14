package eu.geniusgamedev.StepLink.metadata.repository;

import eu.geniusgamedev.StepLink.metadata.entity.EventInviteLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EventInviteLinkRepository extends JpaRepository<EventInviteLink, Long> {
    Optional<EventInviteLink> findByOwnerIdAndEventId(Long ownerId, Long eventId);
    Optional<EventInviteLink> findByUniqueLink(String uniqueLink);
}
