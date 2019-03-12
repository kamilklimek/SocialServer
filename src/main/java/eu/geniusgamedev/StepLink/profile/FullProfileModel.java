package eu.geniusgamedev.StepLink.profile;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class FullProfileModel extends ProfileModel {
    private List<ProfileModel> following;
    private List<ProfileModel> followers;

    private FullProfileModel(Long id, String name, String lastName, String email, List<ProfileModel> following, List<ProfileModel> followers) {
        super(id, name, lastName, email);
        this.following = following;
        this.followers = followers;
    }

    public static class Builder {
        private List<ProfileModel> following = new LinkedList<>();
        private List<ProfileModel> followers = new LinkedList<>();
        private Long id;
        private String name;
        private String lastName;
        private String email;

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

        public FullProfileModel build() {
            return new FullProfileModel(id, name, lastName, email, following, followers);
        }

    }
}
