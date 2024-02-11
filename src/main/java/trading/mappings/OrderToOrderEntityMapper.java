package trading.mappings;

import org.mapstruct.Mapper;
import trading.entities.OrderEntity;
import trading.models.Order;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderToOrderEntityMapper {
    OrderEntity orderToOrderEntity(Order order);
    Order orderEntityToOrder(OrderEntity entity);
    List<OrderEntity> orderToOrderEntity(List<Order> order);
    List<Order> orderEntityToOrder(List<OrderEntity> entity);
}
