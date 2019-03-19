package eu.geniusgamedev.StepLink.profile;

import eu.geniusgamedev.StepLink.events.models.EventModel;
import eu.geniusgamedev.StepLink.notifications.NotificationModel;
import lombok.Getter;

import java.util.List;

@Getter
public class AuthenticationFullProfileModel extends FullProfileModel {
    private String token;

    private AuthenticationFullProfileModel(Long id, String name, String lastName, String email,
                                           List<ProfileModel> following, List<ProfileModel> followers,
                                           List<EventModel> joinedEvents, List<NotificationModel> notifications, String token) {
        super(id, name, lastName, email, following, followers, joinedEvents, notifications);
        this.token = token;
    }

    public static class AuthenticationBuilder {
        private String token;
        private FullProfileModel fullProfileModel;

        public AuthenticationBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AuthenticationBuilder fromProfileModel(FullProfileModel profileModel) {
            this.fullProfileModel = profileModel;
            return this;
        }


        public AuthenticationFullProfileModel build() {
            return new AuthenticationFullProfileModel(fullProfileModel.getId(), fullProfileModel.getName(),
                    fullProfileModel.getLastName(), fullProfileModel.getEmail(),
                    fullProfileModel.getFollowing(), fullProfileModel.getFollowers(),
                    fullProfileModel.getJoinedEvents(), fullProfileModel.getNotifications(), token);
        }
    }
}
