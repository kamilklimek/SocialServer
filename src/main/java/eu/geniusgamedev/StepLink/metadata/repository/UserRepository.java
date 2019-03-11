package eu.geniusgamedev.StepLink.metadata.repository;

import eu.geniusgamedev.StepLink.metadata.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
