package trading.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trading.entities.OrderEntity;
import trading.models.OrderType;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByTypeAndSecurityId(OrderType type, Long securityId);
}
