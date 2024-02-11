package trading.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private User user;
    private Security security;
    private OrderType type;
    private float price;
    private int quantity;
    private boolean fullfilled;
}
