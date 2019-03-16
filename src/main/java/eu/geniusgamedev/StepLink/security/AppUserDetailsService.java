package eu.geniusgamedev.StepLink.security;

import eu.geniusgamedev.StepLink.metadata.UserMetaDataService;
import eu.geniusgamedev.StepLink.metadata.entity.User;
import eu.geniusgamedev.StepLink.security.authorization.UserIdentity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
@RequiredArgsConstructor
@Slf4j
public class AppUserDetailsService implements UserDetailsService {
    private final UserMetaDataService userMetaDataService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        log.info("Loading user by email... {} ", email);

        final User user =  userMetaDataService.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " cannot be found."));


        UserIdentity userIdentity = UserIdentity.builder()
                .email(email)
                .password(user.getPassword())
                .userId(user.getId())
                .build();

        return userIdentity;
    }
}
