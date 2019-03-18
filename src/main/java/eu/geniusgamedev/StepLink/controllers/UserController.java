package eu.geniusgamedev.StepLink.controllers;

import eu.geniusgamedev.StepLink.metadata.UserMetaDataService;
import eu.geniusgamedev.StepLink.profile.ProfileModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserMetaDataService metaDataService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfileModel> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(metaDataService.findUserById(id));
        } catch (Exception e) {
            log.error("Caught error due to: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProfileModel>> getUsers(@RequestParam(name = "search", defaultValue = "") String search) {
        try {
            return ResponseEntity.ok(metaDataService.findAllUsers(search));
        }catch (Exception e) {
            log.error("Caught error due to: {}", e.getMessage(), e);
            throw e;
        }
    }
}
