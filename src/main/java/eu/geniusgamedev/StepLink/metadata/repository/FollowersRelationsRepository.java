package eu.geniusgamedev.StepLink.metadata.repository;

import eu.geniusgamedev.StepLink.metadata.entity.FollowersRelations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowersRelationsRepository extends JpaRepository<FollowersRelations, Long> {
    boolean existsByFollowerIdAndFollowedId(Long follower, Long followed);
    void deleteByFollowerIdAndFollowedId(Long follower, Long followed);
}
