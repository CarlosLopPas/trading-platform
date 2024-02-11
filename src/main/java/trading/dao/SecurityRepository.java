package trading.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trading.entities.SecurityEntity;

@Repository
public interface SecurityRepository extends JpaRepository<SecurityEntity, Long> {
}
