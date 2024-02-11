package trading.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TradeEntity {
    @Id
    private long id;
    @OneToOne
    private OrderEntity sellOrder;
    @OneToOne
    private OrderEntity buyOrder;
    @Column
    private float price;
    @Column
    private int quantity;
}
