package eu.geniusgamedev.StepLink.profile;

import eu.geniusgamedev.StepLink.events.models.EventModel;
import eu.geniusgamedev.StepLink.metadata.entity.Notification;
import eu.geniusgamedev.StepLink.notifications.NotificationModel;
import lombok.Getter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter
public class FullProfileModel extends ProfileModel {
    private Set<ProfileModel> following;
    private Set<ProfileModel> followers;
    private Set<EventModel> joinedEvents;
    private List<NotificationModel> notifications;



    protected FullProfileModel(Long id, String name, String lastName, String email, Set<ProfileModel> following, Set<ProfileModel> followers, Set<EventModel> joinedEvents, List<NotificationModel> notifications) {
        super(id, name, lastName, email);
        this.following = following;
        this.followers = followers;
        this.joinedEvents = joinedEvents;
        this.notifications = notifications;
    }

    public static class Builder {
        private Set<ProfileModel> following = new HashSet<>();
        private Set<ProfileModel> followers = new HashSet<>();
        private Set<EventModel> joinedEvents = new HashSet<>();
        private List<NotificationModel> notifications = new LinkedList<>();

        protected Long id;
        protected String name;
        private String lastName;
        protected String email;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder following(Set<ProfileModel> following) {
            this.following = following;
            return this;
        }

        public Builder followers(Set<ProfileModel> followers) {
            this.followers = followers;
            return this;
        }

        public Builder notifications(List<NotificationModel> notifications) {
            this.notifications = notifications;
            return this;
        }
        public Builder joinedEvents(Set<EventModel> events) {
            this.joinedEvents = events;
            return this;
        }

        public FullProfileModel build() {
            return new FullProfileModel(id, name, lastName, email, following, followers, joinedEvents, notifications);
        }

    }
}
