package trading.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import trading.models.OrderType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderEntity {
    @Id
    private long id;
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private SecurityEntity security;
    @Column
    private OrderType type;
    @Column
    private float price;
    @Column
    private int quantity;
    @Column
    private boolean fullfilled;
}
