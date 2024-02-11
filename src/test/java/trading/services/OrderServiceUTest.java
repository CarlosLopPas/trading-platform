package trading.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import trading.dao.OrderRepository;
import trading.entities.OrderEntity;
import trading.entities.SecurityEntity;
import trading.entities.UserEntity;
import trading.mappings.OrderToOrderEntityMapper;
import trading.models.Order;
import trading.models.OrderType;
import trading.models.Security;
import trading.models.User;
import trading.services.OrderService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class OrderServiceUTest {
    private OrderToOrderEntityMapper mapper = mock(OrderToOrderEntityMapper.class);
    private OrderRepository repo = mock(OrderRepository.class);
    private OrderService service = new OrderService(repo, mapper);

    @Test
    public void testSearchForExistingId() {
        Long id = 1L;
        UserEntity userEntity = new UserEntity(1L, "testuser", "testpass");
        User userModel = new User(1L, "testuser", "testpass");
        SecurityEntity securityEntity = new SecurityEntity(1L, "testname");
        Security securityModel = new Security(1L, "testname");
        OrderEntity entity = new OrderEntity(id, userEntity, securityEntity, OrderType.SELL, 1.2f, 3, true);
        Order model = new Order(id, userModel, securityModel, OrderType.SELL, 1.2f, 3, true);

        when(mapper.orderEntityToOrder(entity)).thenReturn(model);
        when(repo.findById(id)).thenReturn(Optional.of(entity));

        Order resp = service.searchForId(id);

        verify(repo).findById(id);
        assertEquals(model, resp);
    }

    @Test
    public void testSearchForNotExistingId() {
        when(repo.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.searchForId(1L));
    }

    @Test
    public void testSearchByTypeAndSecurity() {
        Long id = 1L;
        UserEntity userEntity = new UserEntity(1L, "testuser", "testpass");
        User userModel = new User(1L, "testuser", "testpass");
        SecurityEntity securityEntity = new SecurityEntity(1L, "testname");
        Security securityModel = new Security(1L, "testname");
        OrderEntity entity = new OrderEntity(id, userEntity, securityEntity, OrderType.SELL, 1.2f, 3, true);
        Order model = new Order(id, userModel, securityModel, OrderType.SELL, 1.2f, 3, true);
        List<OrderEntity> entitiesList = List.of(entity);
        List<Order> modelList = List.of(model);

        when(mapper.orderEntityToOrder(entitiesList)).thenReturn(modelList);
        when(repo.findByTypeAndSecurityId(OrderType.SELL, id)).thenReturn(entitiesList);

        List<Order> resp = service.searchByTypeAndSecurity(OrderType.SELL, id);

        assertNotNull(resp);
        assertEquals(modelList, resp);
    }

    @Test
    public void testSearchByTypeAndUserAndSecurityNullValues() {
        Assertions.assertThrows(NullPointerException.class, () -> service.searchByTypeAndSecurity(null, 1L));
        Assertions.assertThrows(NullPointerException.class, () -> service.searchByTypeAndSecurity(OrderType.SELL, null));
    }

    @Test
    public void testCreateOrder() {
        Long id = 1L;
        UserEntity userEntity = new UserEntity(1L, "testuser", "testpass");
        User userModel = new User(1L, "testuser", "testpass");
        SecurityEntity securityEntity = new SecurityEntity(1L, "testname");
        Security securityModel = new Security(1L, "testname");
        OrderEntity entity = new OrderEntity(id, userEntity, securityEntity, OrderType.SELL, 1.2f, 3, true);
        Order model = new Order(id, userModel, securityModel, OrderType.SELL, 1.2f, 3, true);
        List<OrderEntity> entitiesList = List.of(entity);
        List<Order> modelList = List.of(model);

        when(mapper.orderToOrderEntity(modelList)).thenReturn(entitiesList);
        when(repo.saveAll(entitiesList)).thenReturn(entitiesList);

        service.createOrders(modelList);

        verify(repo).saveAll(entitiesList);
    }

    @Test
    public void testCreateOrderNullValue() {
        Assertions.assertThrows(NullPointerException.class, () -> service.createOrders(null));
    }

    @Test
    public void testDeleteNullListUsers() {
        Assertions.assertThrows(NullPointerException.class, () -> service.deleteOrders(null));
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        UserEntity userEntity = new UserEntity(1L, "testuser", "testpass");
        User userModel = new User(1L, "testuser", "testpass");
        SecurityEntity securityEntity = new SecurityEntity(1L, "testname");
        Security securityModel = new Security(1L, "testname");
        OrderEntity entity = new OrderEntity(id, userEntity, securityEntity, OrderType.SELL, 1.2f, 3, true);
        Order model = new Order(id, userModel, securityModel, OrderType.SELL, 1.2f, 3, true);
        List<OrderEntity> entitiesList = List.of(entity);
        List<Order> modelList = List.of(model);

        when(mapper.orderToOrderEntity(modelList)).thenReturn(entitiesList);

        service.deleteOrders(modelList);

        verify(repo).deleteAll(entitiesList);
    }
}
