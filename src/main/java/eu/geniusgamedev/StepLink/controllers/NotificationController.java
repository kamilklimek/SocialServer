package eu.geniusgamedev.StepLink.controllers;

import eu.geniusgamedev.StepLink.metadata.NotificationService;
import eu.geniusgamedev.StepLink.notifications.NotificationModel;
import eu.geniusgamedev.StepLink.security.authorization.UserIdentity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NotificationModel>> getUserNotifications(UsernamePasswordAuthenticationToken principal) {

        UserIdentity userIdentity = (UserIdentity) principal.getPrincipal();

        try {
            return ResponseEntity.ok(notificationService.getNotifications(userIdentity));
        } catch (Exception e) {
            log.error("Caught error: {} while tried to get user's: {} notifications. ", e.getMessage(), userIdentity);
            throw e;
        }
    }
}
