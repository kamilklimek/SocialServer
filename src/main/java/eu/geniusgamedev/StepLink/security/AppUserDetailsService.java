package eu.geniusgamedev.StepLink.security;

import eu.geniusgamedev.StepLink.metadata.UserMetaDataService;
import eu.geniusgamedev.StepLink.metadata.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final UserMetaDataService userMetaDataService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        final User user =  userMetaDataService.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " cannot be found."));

        return org.springframework.security.core.userdetails.User.withUsername(email)
                .password(user.getPassword()).authorities(Collections.emptyList())
                .accountExpired(false).accountLocked(false).credentialsExpired(false)
                .disabled(false).build();
    }
}
