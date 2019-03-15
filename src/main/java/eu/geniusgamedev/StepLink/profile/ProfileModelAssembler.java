package eu.geniusgamedev.StepLink.profile;

import eu.geniusgamedev.StepLink.events.EventAssembler;
import eu.geniusgamedev.StepLink.events.models.EventModel;
import eu.geniusgamedev.StepLink.metadata.entity.Event;
import eu.geniusgamedev.StepLink.metadata.entity.FollowersRelations;
import eu.geniusgamedev.StepLink.metadata.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProfileModelAssembler {
    private final EventAssembler eventAssembler;

    public FullProfileModel convertEntityToFullProfileModel(User user) {
        return new FullProfileModel.Builder()
                .email(user.getEmail())
                .id(user.getId())
                .lastName(user.getLastName())
                .following(getAllFollowing(user))
                .followers(getAllFollowers(user))
                .name((user.getName()))
                .joinedEvents(convertEvents(user.getJoinedEvents()))
                .build();
    }

    private List<EventModel> convertEvents(List<Event> joinedEvents) {
        return joinedEvents.stream()
                .map(eventAssembler::convertFromEntity)
                .collect(Collectors.toList());
    }

    public ProfileModel convertEntityToProfileModel(User user) {
        return ProfileModel.builder()
                .email(user.getEmail())
                .id(user.getId())
                .lastName(user.getLastName())
                .name((user.getName()))
                .build();
    }

    private List<ProfileModel> getAllFollowers(User user) {
        return user.getFollowed().stream()
                .map(FollowersRelations::getFollower)
                .map(this::convertEntityToModel)
                .collect(Collectors.toList());
    }

    private ProfileModel convertEntityToModel(User user) {
        return ProfileModel.builder()
                .name(user.getName())
                .email(user.getEmail())
                .id(user.getId())
                .lastName(user.getLastName())
                .build();
    }

    private List<ProfileModel> getAllFollowing(User user) {
        return user.getFollowing().stream()
                .map(FollowersRelations::getFollowed)
                .map(this::convertEntityToModel)
                .collect(Collectors.toList());
    }
}
