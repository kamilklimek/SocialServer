package eu.geniusgamedev.StepLink.metadata.repository;

import eu.geniusgamedev.StepLink.metadata.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
