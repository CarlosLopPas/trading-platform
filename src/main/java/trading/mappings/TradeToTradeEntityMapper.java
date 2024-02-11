package trading.mappings;

import org.mapstruct.Mapper;
import trading.entities.TradeEntity;
import trading.models.Trade;

@Mapper(componentModel = "spring")
public interface TradeToTradeEntityMapper {
    Trade tradeEntityToTrade(TradeEntity entity);
    TradeEntity tradeToTradeEntity(Trade model);
}
