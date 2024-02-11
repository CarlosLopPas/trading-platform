package trading.api;

import org.springframework.web.bind.annotation.*;
import trading.models.Order;
import trading.services.OrderService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/order")
public class OrderRest {
    private final OrderService service;

    public OrderRest(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public Order getOrder(@RequestBody Long id) {
        Objects.requireNonNull(id);
        return service.searchForId(id);
    }

    @PostMapping
    public void createOrders(@RequestBody List<Order> orders) {
        service.createOrders(orders);
    }

    @DeleteMapping
    public void deleteOrders(@RequestBody List<Order> orders) {
        service.deleteOrders(orders);
    }
}
