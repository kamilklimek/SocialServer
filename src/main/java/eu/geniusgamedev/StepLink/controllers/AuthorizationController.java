package eu.geniusgamedev.StepLink.controllers;

import eu.geniusgamedev.StepLink.metadata.UserMetaDataService;
import eu.geniusgamedev.StepLink.metadata.entity.User;
import eu.geniusgamedev.StepLink.profile.FullProfileModel;
import eu.geniusgamedev.StepLink.security.authorization.TokenProvider;
import eu.geniusgamedev.StepLink.security.login.UserLoginModel;
import eu.geniusgamedev.StepLink.security.register.UserRegisterModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthorizationController {
    private final UserMetaDataService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @PostMapping(
            value = "/signup",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FullProfileModel> register(@RequestBody UserRegisterModel model) {

        try {
            return ResponseEntity.ok(userService.register(model));
        } catch (Exception e) {
            log.error("Caught exception: {} while try to register new user: {}.", e.getMessage(), model);
            throw e;
        }
    }

    @PostMapping(
            value ="/login",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody UserLoginModel model) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                model.getEmail(), model.getPassword());

        try {
            authenticationManager.authenticate(authenticationToken);
            return ResponseEntity.ok(tokenProvider.createToken(model.getEmail()));
        }
        catch (AuthenticationException e) {
            log.info("User: {} is unauthorized: {}.", model, e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You typed wrong email or password.");
        }
        catch (Exception e) {
            log.error("UserLoginModel: {}, error caught due to: {}", model, e.getMessage(), e);
            throw e;
        }
    }
}
