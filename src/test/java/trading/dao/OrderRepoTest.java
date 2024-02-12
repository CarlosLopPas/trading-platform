package trading.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import trading.entities.OrderEntity;
import trading.entities.SecurityEntity;
import trading.entities.UserEntity;
import trading.models.OrderType;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OrderRepoTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private OrderRepository repo;

    @Test
    public void createOrderTest() {
        UserEntity userEntity = new UserEntity(1L, "testuser", "testpass");
        testEntityManager.persist(userEntity);
        SecurityEntity securityEntity = new SecurityEntity(1L, "testname");
        testEntityManager.persist(securityEntity);
        testEntityManager.flush();
        OrderEntity entity = new OrderEntity(1L, userEntity, securityEntity, OrderType.SELL, 1.2f, 3, true);

        repo.save(entity);

        OrderEntity resp = testEntityManager.find(OrderEntity.class, 1L);
        assertNotNull(resp);
        assertEquals(entity, resp);
    }

    @Test
    public void testFindById() {
        UserEntity userEntity = new UserEntity(1L, "testuser", "testpass");
        testEntityManager.persist(userEntity);
        SecurityEntity securityEntity = new SecurityEntity(1L, "testname");
        testEntityManager.persist(securityEntity);
        OrderEntity entity = new OrderEntity(1L, userEntity, securityEntity, OrderType.SELL, 1.2f, 3, true);
        testEntityManager.persist(entity);
        testEntityManager.flush();

        Optional<OrderEntity> resp = repo.findById(1L);
        assertNotNull(resp);
        assertTrue(resp.isPresent());
        assertEquals(entity, resp.get());
    }

    @Test
    public void deleteOrderTest() {
        UserEntity userEntity = new UserEntity(1L, "testuser", "testpass");
        testEntityManager.persist(userEntity);
        SecurityEntity securityEntity = new SecurityEntity(1L, "testname");
        testEntityManager.persist(securityEntity);
        OrderEntity entity = new OrderEntity(1L, userEntity, securityEntity, OrderType.SELL, 1.2f, 3, true);
        testEntityManager.persist(entity);
        testEntityManager.flush();

        repo.delete(entity);

        Optional<OrderEntity> r = repo.findById(1L);
        assertFalse(r.isPresent());
    }
}
