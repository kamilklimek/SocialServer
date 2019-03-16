package eu.geniusgamedev.StepLink.aop;

import eu.geniusgamedev.StepLink.events.models.EventCreateModel;
import eu.geniusgamedev.StepLink.metadata.NotificationService;
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
public class EventSystemAspect {
    private final NotificationService notificationService;

    @After("execution(* eu.geniusgamedev.StepLink.metadata.EventService.addEvent(..))")
    public void saveNotificationWhenSomeoneCreateEvent(JoinPoint joinPoint) {
        log.info("Saving notifications about new event, args: {}", joinPoint.getArgs());

        EventCreateModel event = getEventFromArgs(joinPoint.getArgs());

        notificationService.saveNotifications("There is a new event: " + event.getName() + " at: " + event.getLocation() + "!");
    }

    private EventCreateModel getEventFromArgs(Object[] args) {
        return (EventCreateModel) args[0];
    }
}
