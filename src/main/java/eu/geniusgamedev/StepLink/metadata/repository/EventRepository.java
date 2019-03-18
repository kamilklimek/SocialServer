package eu.geniusgamedev.StepLink.metadata.repository;

import eu.geniusgamedev.StepLink.metadata.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    boolean existsEventById(Long id);
    boolean existsByIdAndAttachedUsersId(Long id, Long attachedUsersId);
}
