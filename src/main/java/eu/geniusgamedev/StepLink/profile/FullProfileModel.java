package eu.geniusgamedev.StepLink.profile;

import eu.geniusgamedev.StepLink.events.models.EventModel;
import eu.geniusgamedev.StepLink.metadata.entity.Notification;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class FullProfileModel extends ProfileModel {
    private List<ProfileModel> following;
    private List<ProfileModel> followers;
    private List<EventModel> joinedEvents;
    private List<Notification> notifications;



    protected FullProfileModel(Long id, String name, String lastName, String email, List<ProfileModel> following, List<ProfileModel> followers, List<EventModel> joinedEvents, List<Notification> notifications) {
        super(id, name, lastName, email);
        this.following = following;
        this.followers = followers;
        this.joinedEvents = joinedEvents;
        this.notifications = notifications;
    }

    public static class Builder {
        private List<ProfileModel> following = new LinkedList<>();
        private List<ProfileModel> followers = new LinkedList<>();
        private List<EventModel> joinedEvents = new LinkedList<>();
        private List<Notification> notifications = new LinkedList<>();

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

        public Builder following(List<ProfileModel> following) {
            this.following = following;
            return this;
        }

        public Builder followers(List<ProfileModel> followers) {
            this.followers = followers;
            return this;
        }

        public Builder notifications(List<Notification> notifications) {
            this.notifications = notifications;
            return this;
        }
        public Builder joinedEvents(List<EventModel> events) {
            this.joinedEvents = events;
            return this;
        }

        public FullProfileModel build() {
            return new FullProfileModel(id, name, lastName, email, following, followers, joinedEvents, notifications);
        }

    }
}
