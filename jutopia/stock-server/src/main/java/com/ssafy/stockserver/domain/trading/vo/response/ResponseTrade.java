package com.ssafy.stockserver.domain.trading.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.stockserver.domain.stock.entity.Stock;
import com.ssafy.stockserver.domain.trading.entity.TradeType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseTrade {
    private UUID id;

    private String stockName;
    private String stockCode;

    private UUID memberId;
    private TradeType type;
    private Long volume;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private LocalTime tradeAt;

}
