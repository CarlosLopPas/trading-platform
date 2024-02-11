package trading.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import trading.models.User;
import trading.services.UserService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserRest {
    private final UserService service;

    public UserRest(UserService service) {
        this.service = service;
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@RequestBody Long id) {
        Objects.requireNonNull(id);
        return service.searchById(id);
    }

    @PostMapping
    public void createUsers(@RequestBody List<User> users) {
        service.createUsers(users);
    }

    @DeleteMapping
    public void deleteUsers(@RequestBody List<User> users) {
        service.deleteUsers(users);
    }
}
