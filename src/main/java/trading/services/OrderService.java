package trading.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import trading.dao.OrderRepository;
import trading.mappings.OrderToOrderEntityMapper;
import trading.models.Order;
import trading.models.OrderType;

import java.util.List;
import java.util.Objects;

@Service
public class OrderService {
    private final OrderRepository repo;
    private final OrderToOrderEntityMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository repo, OrderToOrderEntityMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public Order searchForId(long id) {
        log.info("Searching for order with ID {}", id);
        return mapper.orderEntityToOrder(
            repo.findById(id).orElseThrow(() -> new IllegalArgumentException("No suitable ID found searching")));
    }

    public List<Order> searchByTypeAndSecurity(OrderType type, Long securityId) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(securityId);

        log.info("Searching for an order of type {} and security ID {}", type, securityId);

        return mapper.orderEntityToOrder(repo.findByTypeAndSecurityId(type, securityId));
    }

    public void createOrders(List<Order> orders) {
        Objects.requireNonNull(orders);

        if (orders.isEmpty()) {
            log.warn("Empty order objects list received while creating. Nothing done");
            return;
        }

        log.info("Creating given order list");
        log.debug("Order list: {}", orders);

        repo.saveAll(mapper.orderToOrderEntity(orders));
    }

    public void deleteOrders(List<Order> orders) {
        Objects.requireNonNull(orders);

        if (orders.isEmpty()) {
            log.warn("Empty order objects list received while deleting. Nothing done");
            return;
        }

        log.info("Deleting given order list");
        log.debug("Order list: {}", orders);

        repo.deleteAll(mapper.orderToOrderEntity(orders));
    }
}
