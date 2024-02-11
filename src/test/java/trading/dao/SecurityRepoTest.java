package trading.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import trading.dao.SecurityRepository;
import trading.entities.SecurityEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class SecurityRepoTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private SecurityRepository repo;

    @Test
    public void createSecurityTest() {
        SecurityEntity security = new SecurityEntity(1L, "name");
        repo.save(security);

        SecurityEntity resp = testEntityManager.find(SecurityEntity.class, 1L);
        assertNotNull(resp);
        assertEquals(security, resp);
    }

    @Test
    public void testFindById() {
        SecurityEntity security = new SecurityEntity(1L, "name");
        testEntityManager.persist(security);
        testEntityManager.flush();

        Optional<SecurityEntity> resp = repo.findById(1L);
        assertNotNull(resp);
        assertTrue(resp.isPresent());
        assertEquals(security, resp.get());
    }

    @Test
    public void deleteUserTest() {
        SecurityEntity security = new SecurityEntity(1L, "name");
        testEntityManager.persist(security);
        testEntityManager.flush();

        repo.delete(security);

        Optional<SecurityEntity> r = repo.findById(1L);
        assertFalse(r.isPresent());
    }
}
