package trading.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import trading.entities.UserEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepoTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private UserRepository repo;

    @Test
    public void createUserTest() {
        UserEntity user = new UserEntity(1L, "name", "pass");
        repo.save(user);

        UserEntity k = testEntityManager.find(UserEntity.class, 1L);
        assertNotNull(k);
        assertEquals(user, k);
    }

    @Test
    public void testFindById() {
        UserEntity user = new UserEntity(1L, "name", "pass");
        testEntityManager.persist(user);
        testEntityManager.flush();

        Optional<UserEntity> resp = repo.findById(1L);
        assertNotNull(resp);
        assertTrue(resp.isPresent());
        assertEquals(user, resp.get());
    }

    @Test
    public void testFindByNameAndPassword() {
        UserEntity user = new UserEntity(1L, "name", "pass");
        testEntityManager.persist(user);
        testEntityManager.flush();

        UserEntity r = repo.findByNameAndPassword("name", "pass");
        assertNotNull(r);
        assertEquals(user, r);
    }

    @Test
    public void deleteUserTest() {
        UserEntity user = new UserEntity(1L, "name", "pass");
        testEntityManager.persist(user);
        testEntityManager.flush();

        repo.delete(user);

        Optional<UserEntity> r = repo.findById(1L);
        assertFalse(r.isPresent());
    }
}
