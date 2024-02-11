package trading.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trade {
    private Long id;
    private Order sellOrder;
    private Order buyOrder;
    private float price;
    private int quantity;
}
