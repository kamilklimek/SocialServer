package eu.geniusgamedev.StepLink.metadata;

import eu.geniusgamedev.StepLink.metadata.entity.User;
import eu.geniusgamedev.StepLink.metadata.repository.UserRepository;
import eu.geniusgamedev.StepLink.security.register.UserRegisterAssembler;
import eu.geniusgamedev.StepLink.security.register.UserRegisterModel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserMetaDataService {
    private final UserRepository userRepository;
    private final UserRegisterAssembler assembler;
    private final PasswordEncoder encoder;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User register(UserRegisterModel model) {
        final User user = assembler.convertModelToDao(model);

        encodePassword(user);

        return userRepository.save(user);
    }

    private void encodePassword(User user) {
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
