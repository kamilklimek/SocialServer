package eu.geniusgamedev.StepLink.metadata;

import eu.geniusgamedev.StepLink.metadata.entity.User;
import eu.geniusgamedev.StepLink.metadata.repository.UserRepository;
import eu.geniusgamedev.StepLink.metadata.search.SpecificationFactory;
import eu.geniusgamedev.StepLink.metadata.search.SpecificationProxy;
import eu.geniusgamedev.StepLink.metadata.search.UserSpecification;
import eu.geniusgamedev.StepLink.profile.FullProfileModel;
import eu.geniusgamedev.StepLink.profile.ProfileModel;
import eu.geniusgamedev.StepLink.profile.ProfileModelAssembler;
import eu.geniusgamedev.StepLink.security.register.UserRegisterAssembler;
import eu.geniusgamedev.StepLink.security.register.UserRegisterModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@Transactional
@AllArgsConstructor
public class UserMetaDataService {
    private final UserRepository userRepository;
    private final UserRegisterAssembler assembler;
    private final PasswordEncoder encoder;
    private final ProfileModelAssembler profileAssembler;
    private final SpecificationFactory specificationFactory;

    public List<ProfileModel> findAllUsers(String search) {
        SpecificationProxy specification = specificationFactory.createProxy(new UserSpecification(search));

        return profileAssembler.convertEntitiesToProfileModels(userRepository.findAll(specification));
    }

    public FullProfileModel register(UserRegisterModel model) {
        log.info("Registering new user: {}. ", model);

        final User user = assembler.convertModelToDao(model);

        encodePassword(user);

        return profileAssembler.convertEntityToFullProfileModel(userRepository.save(user));
    }

    private void encodePassword(User user) {
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public Optional<User> findUserByEmail(String email) {
        log.info("Finding user by email: {}. ", email);
        return userRepository.findByEmail(email);
    }

    public User getUserByEmail(String email) {
        return findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public ProfileModel findUserById(Long id) {
        log.info("Finding user profile by id: {}", id);
        final User user = findUser(id);

        return profileAssembler.convertEntityToFullProfileModel(user);
    }

    public User findUser(Long id) {
        log.info("Finding user by id: {}.", id);

        try {
            return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        } catch (Exception e) {
            log.error("User with id: {} could not be found: {}.", id, e.getMessage());
            throw e;
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    private class UserNotFoundException extends RuntimeException {
    }
}
