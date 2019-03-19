package eu.geniusgamedev.StepLink.security.register;

import eu.geniusgamedev.StepLink.metadata.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserRegisterAssembler {
    public User convertModelToDao(UserRegisterModel model) {
        return User.builder()
                .email(model.getEmail())
                .lastName(model.getLastName())
                .name(model.getName())
                .password(model.getPassword())
                .acceptedInvitations(Collections.emptySet())
                .followed(Collections.emptySet())
                .following(Collections.emptySet())
                .eventInviteLinks(Collections.emptySet())
                .notifications(Collections.emptyList())
                .joinedEvents(Collections.emptySet())
                .build();
    }

}
