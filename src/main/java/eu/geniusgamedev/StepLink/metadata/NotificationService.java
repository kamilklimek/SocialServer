package eu.geniusgamedev.StepLink.metadata;

import eu.geniusgamedev.StepLink.metadata.repository.NotificationRepository;
import eu.geniusgamedev.StepLink.notifications.NotificationAssembler;
import eu.geniusgamedev.StepLink.notifications.NotificationModel;
import eu.geniusgamedev.StepLink.security.authorization.UserIdentity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationAssembler notificationAssembler;

    public List<NotificationModel> getNotifications(UserIdentity userIdentity) {
        return notificationAssembler
                .convertFromEntities(notificationRepository
                        .findAllByOwnerId(userIdentity.getUserId()));
    }
}
