package com.ssafy.stockserver.domain.trading.vo.request;

import com.ssafy.stockserver.domain.stock.entity.Stock;
import com.ssafy.stockserver.domain.trading.entity.TradeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class RequestTrade {
    @NotNull(message = "회원 아이디를 설정하세요")
    private UUID memberId;
    @NotNull(message = "주식종목을 설정하세요")
    private UUID stockId;

    @NotNull(message = "매수/매도 타입을 설정하세요")
    private TradeType type;
    // 거래량
    private Long volume;
    // 거래가
    private BigDecimal price;
}
