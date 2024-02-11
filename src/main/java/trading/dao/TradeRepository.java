package trading.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trading.entities.TradeEntity;

@Repository
public interface TradeRepository extends JpaRepository<TradeEntity, Long> {
}
