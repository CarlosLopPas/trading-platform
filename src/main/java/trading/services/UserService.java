package trading.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import trading.dao.UserRepository;
import trading.mappings.UserToUserEntityMapping;
import trading.models.User;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository repo;
    private final UserToUserEntityMapping mapper;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository repo, UserToUserEntityMapping mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public User searchById(long id) {
        log.info("Searching a user with ID {}", id);
        return mapper.userEntityToUser(
            repo.findById(id).orElseThrow(() -> new IllegalArgumentException("No suitable object found for ID " + id)));
    }

    public boolean checkUser(User user) {
        Objects.requireNonNull(user);
        log.info("Checking the existence of user {}", user);
        return !Objects.isNull(repo.findByNameAndPassword(user.getName(), user.getPassword()));
    }

    public void createUsers(List<User> users) {
        Objects.requireNonNull(users);

        log.info("Creating user list");
        log.debug("User list: {}", users);

        if (users.isEmpty()) {
            log.warn("Empty users object list received while creating. Nothing done");
            return;
        }

        log.info("Creating given users");
        log.debug("Users: {}", users);

        repo.saveAll(mapper.userToUserEntity(users));
    }

    public void deleteUsers(List<User> users) {
        Objects.requireNonNull(users);

        if (users.isEmpty()) {
            log.warn("Empty users object list received while deleting. Nothing done");
            return;
        }

        log.info("Deleting given users");
        log.debug("Users: {}", users);

        repo.deleteAll((mapper.userToUserEntity(users)));
    }
}
