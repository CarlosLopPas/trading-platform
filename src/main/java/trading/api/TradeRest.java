package trading.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trading.models.Security;
import trading.models.Trade;
import trading.services.TradeService;

import java.util.Optional;

@RestController
@RequestMapping("/trade")
public class TradeRest {
    private final TradeService tradeService;

    public TradeRest(TradeService service) {
        this.tradeService = service;
    }

    @PostMapping
    public Optional<Trade> tryTrade(@RequestBody Security security) {
        return tradeService.tryTrade(security);
    }
}
