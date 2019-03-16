package eu.geniusgamedev.StepLink.aop;

import eu.geniusgamedev.StepLink.metadata.NotificationService;
import eu.geniusgamedev.StepLink.security.authorization.UserIdentity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class FollowingSystemAspect {
    private final NotificationService notificationService;

    @After("execution(* eu.geniusgamedev.StepLink.metadata.FollowerService.followUser(..))")
    public void saveNotificationWhenSomeoneFollowYou(JoinPoint joinPoint) {
        log.info("Saving notifications about new follower, args: {}", joinPoint.getArgs());
        Object [] args = joinPoint.getArgs();

        UserIdentity currentLoggedUserIdentity = getUserIdentity(args);
        Long userIdToFollow = getUserIdToFollow(args);

        notificationService.saveNotification(userIdToFollow, "User: " + currentLoggedUserIdentity.getUsername() + " followed you.");
    }

    private Long getUserIdToFollow(Object[] args) {
        return (Long) args[1];
    }

    private UserIdentity getUserIdentity(Object[] args) {
        return (UserIdentity) args[0];
    }
}
