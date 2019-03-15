package eu.geniusgamedev.StepLink.notifications;

import eu.geniusgamedev.StepLink.metadata.entity.Notification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationAssembler {
    public NotificationModel convertFromEntity(Notification notification) {
        return NotificationModel.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .build();
    }

    public List<NotificationModel> convertFromEntities(List<Notification> notifications) {
        return notifications.stream()
                .map(this::convertFromEntity)
                .collect(Collectors.toList());
    }
}
