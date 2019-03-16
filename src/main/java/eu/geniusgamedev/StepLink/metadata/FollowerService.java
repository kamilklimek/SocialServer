package eu.geniusgamedev.StepLink.metadata;

import eu.geniusgamedev.StepLink.metadata.entity.FollowersRelations;
import eu.geniusgamedev.StepLink.metadata.entity.User;
import eu.geniusgamedev.StepLink.metadata.repository.FollowersRelationsRepository;
import eu.geniusgamedev.StepLink.security.authorization.UserIdentity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FollowerService {
    private final UserMetaDataService userMetaDataService;
    private final FollowersRelationsRepository relationsRepository;

    public void followUser(UserIdentity currentLoggedUser, Long userIdToFollow) {
        log.info("Follow user: {} by current logged user: {}", userIdToFollow, currentLoggedUser);
        validateUserId(currentLoggedUser, userIdToFollow);
        validateExistingRelation(currentLoggedUser.getUserId(), userIdToFollow);

        User currentUser = userMetaDataService.findUser(currentLoggedUser.getUserId());
        User userToFollow = userMetaDataService.findUser(userIdToFollow);

        saveFollowerRelation(currentUser, userToFollow);
    }

    private void validateExistingRelation(Long currentLoggedUser, Long userIdToFollow) {
        boolean exists = relationsRepository.existsByFollowerIdAndFollowedId(currentLoggedUser, userIdToFollow);

        if (exists) {
            throw new FollowersRelationsException();
        }
    }

    private void saveFollowerRelation(User currentUser, User userToFollow) {
        log.info("Add follower: {} to current logged user: {}.", currentUser, userToFollow);

        FollowersRelations relation = FollowersRelations.builder()
                .follower(currentUser)
                .followed(userToFollow)
                .build();

        relationsRepository.save(relation);
    }

    private void validateUserId(UserIdentity currentUser, Long userIdToFollow) {
        if (currentUser.getUserId().equals(userIdToFollow)) {
            log.error("User: {} cannot follow/unfollow yourself (userIdToFollow: {}).", currentUser, userIdToFollow);
            throw new FollowUserException("User: " + currentUser +" cannot follow yourself (userIdToFollow: " + userIdToFollow + ").");
        }
    }

    public void unfollowUser(UserIdentity user, Long userIdToUnfollow) {
        log.info("Unfollowing user: {} by current logged user: {}", userIdToUnfollow, user);
        validateUserId(user, userIdToUnfollow);

        removeFollowerRelations(user.getUserId(), userIdToUnfollow);
    }

    private void removeFollowerRelations(Long currentUserId, Long userIdToUnfollow) {
        relationsRepository.deleteByFollowerIdAndFollowedId(currentUserId, userIdToUnfollow);
    }

    private class FollowUserException extends RuntimeException {
        private FollowUserException(String msg) {
            super(msg);
        }
    }

    private class FollowersRelationsException extends RuntimeException {
        private FollowersRelationsException() {
            super("User already follow another used.");
        }
    }
}
