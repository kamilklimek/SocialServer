package eu.geniusgamedev.StepLink.metadata;

import eu.geniusgamedev.StepLink.metadata.entity.User;
import eu.geniusgamedev.StepLink.metadata.repository.UserRepository;
import eu.geniusgamedev.StepLink.security.register.UserRegisterAssembler;
import eu.geniusgamedev.StepLink.security.register.UserRegisterModel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserMetaDataService {
    private final UserRepository userRepository;
    private final UserRegisterAssembler assembler;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User register(UserRegisterModel model) {
        final User user = assembler.convertModelToDao(model);

        return userRepository.save(user);
    }
}
