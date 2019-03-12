package eu.geniusgamedev.StepLink.controllers;

import eu.geniusgamedev.StepLink.metadata.FollowerService;
import eu.geniusgamedev.StepLink.metadata.entity.User;
import eu.geniusgamedev.StepLink.security.authorization.UserIdentity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FollowerController {
    private final FollowerService followerService;


    @PostMapping(value = "/follow/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> followUser(
            @AuthenticationPrincipal UserIdentity user,
            @PathVariable(name="id") Long userIdToFollow) {

        try {
            followerService.followUser(user, userIdToFollow);
            return ResponseEntity.ok().build();
        } catch(Exception e) {
            log.error("Caught error while try to follow another user with id: {}, by current user: {}", userIdToFollow, user);
            throw e;
        }
    }

    @DeleteMapping(value = "/unfollow/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> unfollowUser(
            @AuthenticationPrincipal UserIdentity user,
            @PathVariable(name = "id") Long userIdToUnfollow) {

        try {
            followerService.unfollowUser(user, userIdToUnfollow);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Caught error while try to unfollow another use with: {}, by current user: {}", userIdToUnfollow, user);
            throw e;
        }
    }

}

