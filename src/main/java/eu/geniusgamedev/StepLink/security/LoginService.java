package eu.geniusgamedev.StepLink.security;

import eu.geniusgamedev.StepLink.metadata.UserMetaDataService;
import eu.geniusgamedev.StepLink.metadata.entity.User;
import eu.geniusgamedev.StepLink.profile.AuthenticationFullProfileModel;
import eu.geniusgamedev.StepLink.profile.ProfileModelAssembler;
import eu.geniusgamedev.StepLink.security.authorization.TokenProvider;
import eu.geniusgamedev.StepLink.security.login.UserLoginModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserMetaDataService userMetaDataService;
    private final ProfileModelAssembler assembler;

    public AuthenticationFullProfileModel loginUser(UserLoginModel model) {
        authenticateUser(model);

        String token = tokenProvider.createToken(model.getEmail());

        final User user = userMetaDataService.getUserByEmail(model.getEmail());

        return assembler.convertEntityToAuthenticatedFullProfileModel(user, token);
    }

    private void authenticateUser(UserLoginModel model) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                model.getEmail(), model.getPassword());

        authenticationManager.authenticate(authenticationToken);
    }


}
