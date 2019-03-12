package eu.geniusgamedev.StepLink.profile;

import eu.geniusgamedev.StepLink.metadata.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ProfileModelAssembler {
    public ProfileModel convertEntityToModel(User user) {
        return ProfileModel.builder()
                .email(user.getEmail())
                .id(user.getId())
                .lastName(user.getLastName())
                .name((user.getName()))
                .build();
    }
}
