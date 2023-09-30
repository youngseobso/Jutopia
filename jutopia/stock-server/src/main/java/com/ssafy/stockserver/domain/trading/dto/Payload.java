package com.ssafy.stockserver.domain.trading.dto;

import com.ssafy.stockserver.domain.trading.entity.TradeType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class Payload {
    private UUID memberId;
    private UUID stockId;

    private TradeType type;
    private Long volume;
    private BigDecimal price;
    private BigDecimal totalPrice;
}
