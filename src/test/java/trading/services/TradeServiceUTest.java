package trading.services;

import org.junit.jupiter.api.Test;
import trading.dao.TradeRepository;
import trading.entities.OrderEntity;
import trading.entities.SecurityEntity;
import trading.entities.TradeEntity;
import trading.entities.UserEntity;
import trading.mappings.TradeToTradeEntityMapper;
import trading.models.Order;
import trading.models.OrderType;
import trading.models.Security;
import trading.models.User;
import trading.models.Trade;
import trading.services.OrderService;
import trading.services.TradeService;

import java.util.List;

import static org.mockito.Mockito.*;

public class TradeServiceUTest {
    private OrderService orderService = mock(OrderService.class);
    private TradeRepository repo = mock(TradeRepository.class);
    private TradeToTradeEntityMapper mapper = mock(TradeToTradeEntityMapper.class);
    private TradeService service = new TradeService(orderService, repo, mapper);

    @Test
    public void tryTradeTest() {
        Security security = new Security(1L, "name");
        SecurityEntity securityEntity = new SecurityEntity(1L, "name");
        User userModel = new User(1L, "testuser", "testpass");
        UserEntity userEntity = new UserEntity(1L, "testuser", "testpass");
        List<Order> buyOrders = List.of(new Order(1L, userModel, security, OrderType.BUY, 1.2f, 3, true));
        List<Order> sellOrders = List.of(new Order(1L, userModel, security, OrderType.SELL, 1.2f, 3, true));
        OrderEntity orderEntity = new OrderEntity(1L, userEntity, securityEntity, OrderType.BUY, 1.2f, 3, true);
        TradeEntity tradeEntity = new TradeEntity(1L, orderEntity, orderEntity, 1.2f, 3);

        when(orderService.searchByTypeAndSecurity(OrderType.BUY, 1L)).thenReturn(buyOrders);
        when(orderService.searchByTypeAndSecurity(OrderType.SELL, 1L)).thenReturn(sellOrders);
        when(mapper.tradeToTradeEntity(any(Trade.class))).thenReturn(tradeEntity);

        service.tryTrade(security);

        verify(repo).save(any(TradeEntity.class));
    }
}
