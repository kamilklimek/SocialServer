package eu.geniusgamedev.StepLink.controllers;

import eu.geniusgamedev.StepLink.metadata.UserMetaDataService;
import eu.geniusgamedev.StepLink.metadata.entity.User;
import eu.geniusgamedev.StepLink.security.authorization.TokenProvider;
import eu.geniusgamedev.StepLink.security.register.UserLoginModel;
import eu.geniusgamedev.StepLink.security.register.UserRegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final UserMetaDataService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }


    @PostMapping(
            value = "/signup",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> register(@RequestBody UserRegisterModel model) {

        try {
            return ResponseEntity.ok(userService.register(model));
        } catch (Exception e) {
            throw e;
        }

    }

    @PostMapping(
            value ="/login",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody UserLoginModel model, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                model.getEmail(), model.getPassword());

        try {
            this.authenticationManager.authenticate(authenticationToken);
            return ResponseEntity.ok(tokenProvider.createToken(model.getEmail()));
        }
        catch (AuthenticationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }
}
