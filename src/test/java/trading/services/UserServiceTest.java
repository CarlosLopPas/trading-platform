package trading.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import trading.models.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService service;

    @Test
    public void testCreateAndSearchUser() {
        User user = new User(1L, "name", "pass");
        service.createUsers(List.of(user));

        User resp = service.searchById(1L);
        assertEquals(user, resp);
    }
}
