package eu.geniusgamedev.StepLink.security;

import eu.geniusgamedev.StepLink.metadata.UserMetaDataService;
import eu.geniusgamedev.StepLink.metadata.entity.User;
import eu.geniusgamedev.StepLink.security.authorization.UserIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final UserMetaDataService userMetaDataService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        final User user =  userMetaDataService.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " cannot be found."));


        return UserIdentity.builder()
                .email(email)
                .password(user.getPassword())
                .userId(user.getId())
                .build();
    }
}
