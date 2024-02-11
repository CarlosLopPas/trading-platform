package trading.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import trading.dao.TradeRepository;
import trading.entities.OrderEntity;
import trading.entities.SecurityEntity;
import trading.entities.TradeEntity;
import trading.entities.UserEntity;
import trading.models.OrderType;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TradeRepoTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private TradeRepository repo;

    @Test
    public void createTradeTest() {
        UserEntity userEntity = new UserEntity(1L, "testuser", "testpass");
        testEntityManager.persist(userEntity);
        SecurityEntity securityEntity = new SecurityEntity(1L, "testname");
        testEntityManager.persist(securityEntity);
        OrderEntity orderEntity = new OrderEntity(1L, userEntity, securityEntity, OrderType.SELL, 1.2f, 3, true);
        testEntityManager.persist(orderEntity);
        testEntityManager.flush();
        TradeEntity entity = new TradeEntity(1L, orderEntity, orderEntity, 1.2f, 4);

        repo.save(entity);

        TradeEntity resp = testEntityManager.find(TradeEntity.class, 1L);
        assertNotNull(resp);
        assertEquals(entity, resp);
    }

    @Test
    public void testFindById() {
        UserEntity userEntity = new UserEntity(1L, "testuser", "testpass");
        testEntityManager.persist(userEntity);
        SecurityEntity securityEntity = new SecurityEntity(1L, "testname");
        testEntityManager.persist(securityEntity);
        OrderEntity orderEntity = new OrderEntity(1L, userEntity, securityEntity, OrderType.SELL, 1.2f, 3, true);
        testEntityManager.persist(orderEntity);
        TradeEntity entity = new TradeEntity(1L, orderEntity, orderEntity, 1.2f, 4);
        testEntityManager.persist(entity);
        testEntityManager.flush();

        Optional<TradeEntity> resp = repo.findById(1L);
        assertNotNull(resp);
        assertTrue(resp.isPresent());
        assertEquals(entity, resp.get());
    }

    @Test
    public void deleteTradeTest() {
        UserEntity userEntity = new UserEntity(1L, "testuser", "testpass");
        testEntityManager.persist(userEntity);
        SecurityEntity securityEntity = new SecurityEntity(1L, "testname");
        testEntityManager.persist(securityEntity);
        OrderEntity orderEntity = new OrderEntity(1L, userEntity, securityEntity, OrderType.SELL, 1.2f, 3, true);
        testEntityManager.persist(orderEntity);
        TradeEntity entity = new TradeEntity(1L, orderEntity, orderEntity, 1.2f, 4);
        testEntityManager.persist(entity);

        repo.delete(entity);

        TradeEntity r = testEntityManager.find(TradeEntity.class, 1L);
        assertNull(r);
    }
}
