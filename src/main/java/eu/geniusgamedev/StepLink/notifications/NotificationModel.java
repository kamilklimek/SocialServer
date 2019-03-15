package eu.geniusgamedev.StepLink.notifications;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@ToString
@NoArgsConstructor
public class NotificationModel {
    private Long id;
    private String message;
}
