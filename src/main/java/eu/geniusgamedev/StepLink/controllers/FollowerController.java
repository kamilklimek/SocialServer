package eu.geniusgamedev.StepLink.controllers;

import eu.geniusgamedev.StepLink.metadata.FollowerService;
import eu.geniusgamedev.StepLink.security.authorization.UserIdentity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FollowerController {
    private final FollowerService followerService;


    @PostMapping(value = "/follow/{id}")
    public ResponseEntity<String> followUser(
            UsernamePasswordAuthenticationToken principal,
            @PathVariable(name="id") Long userIdToFollow) {

        UserIdentity user = (UserIdentity) principal.getPrincipal();

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
            UsernamePasswordAuthenticationToken principal,
            @PathVariable(name = "id") Long userIdToUnfollow) {

        UserIdentity user = (UserIdentity) principal.getPrincipal();

        try {
            followerService.unfollowUser(user, userIdToUnfollow);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Caught error while try to unfollow another use with: {}, by current user: {}", userIdToUnfollow, user);
            throw e;
        }
    }

}

