package trading.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import trading.dao.TradeRepository;
import trading.mappings.TradeToTradeEntityMapper;
import trading.models.Order;
import trading.models.OrderType;
import trading.models.Security;
import trading.models.Trade;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TradeService {
    private final OrderService orderService;
    private final TradeRepository repo;
    private final TradeToTradeEntityMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(TradeService.class);

    public TradeService(OrderService orderService, TradeRepository repo, TradeToTradeEntityMapper mapper) {
        this.orderService = orderService;
        this.repo = repo;
        this.mapper = mapper;
    }

    public Optional<Trade> tryTrade(Security security) {
        Objects.requireNonNull(security);

        log.info("Trying to do a trade for the given security object");
        log.debug("Security object given: {}", security);

        Optional<Trade> trade = createTradeIfAvailable(getSellOrders(security.getId()), getBuyOrders(security.getId()));
        trade.ifPresent(t -> repo.save(mapper.tradeToTradeEntity(t)));
        return trade;
    }

    private List<Order> getBuyOrders(Long securityId) {
        return orderService.searchByTypeAndSecurity(OrderType.BUY, securityId);
    }

    private List<Order> getSellOrders(Long securityId) {
        return orderService.searchByTypeAndSecurity(OrderType.SELL, securityId);
    }

    private Optional<Trade> createTradeIfAvailable(List<Order> sellOrders, List<Order> buyOrders) {
        log.info("Looking for a trade order in available orders list");
        log.debug("Available orders lists: sell: {}, buy: {}", sellOrders, buyOrders);

        for (Order b : buyOrders) {
            for (Order s : sellOrders) {
                if ((b.getPrice() >= s.getPrice())) {
                    return Optional.of(new Trade(null, s, b, s.getPrice(), Math.min(b.getQuantity(), s.getQuantity())));
                }
            }
        }

        return Optional.empty();
    }
}
