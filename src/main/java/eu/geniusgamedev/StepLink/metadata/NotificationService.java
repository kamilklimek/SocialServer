package eu.geniusgamedev.StepLink.metadata;

import eu.geniusgamedev.StepLink.metadata.entity.Notification;
import eu.geniusgamedev.StepLink.metadata.entity.User;
import eu.geniusgamedev.StepLink.metadata.repository.NotificationRepository;
import eu.geniusgamedev.StepLink.notifications.NotificationAssembler;
import eu.geniusgamedev.StepLink.notifications.NotificationModel;
import eu.geniusgamedev.StepLink.security.authorization.UserIdentity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationAssembler notificationAssembler;
    private final UserMetaDataService userMetaDataService;

    public List<NotificationModel> getNotifications(UserIdentity userIdentity) {
        return notificationAssembler
                .convertFromEntities(notificationRepository
                        .findAllByOwnerId(userIdentity.getUserId()));
    }

    @Transactional
    public Notification saveNotification(Long userId, String message) {
        final User user = userMetaDataService.findUser(userId);
        Notification notification = new Notification(message, user);

        return notificationRepository.save(notification);
    }

    @Transactional
    public List<Notification> saveNotifications(String message) {
        final List<User> users = userMetaDataService.findAll();

        return saveNotificationForEachUser(users, message);
    }

    private List<Notification> saveNotificationForEachUser(List<User> users, String message) {
        return users.stream()
                .map(user -> saveNotification(user.getId(), message))
                .collect(Collectors.toList());
    }
}
