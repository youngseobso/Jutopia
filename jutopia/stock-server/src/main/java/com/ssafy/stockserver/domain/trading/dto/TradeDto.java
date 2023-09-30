package com.ssafy.stockserver.domain.trading.dto;

import com.ssafy.stockserver.domain.trading.entity.TradeType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TradeDto implements Serializable {
    private UUID memberId;
    private UUID stockId;

    private TradeType type;
    private Long volume;
    private BigDecimal price;
    private BigDecimal totalPrice;

}
