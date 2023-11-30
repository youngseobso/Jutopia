package com.ssafy.stockserver.domain.trading.entity;

import com.ssafy.stockserver.domain.stock.entity.Stock;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "trading")
public class Trading {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TRADING_ID")
    private UUID id;

    // 누구의 거래 인지
    private UUID memberId;

    // 매수 or 매도
    @Enumerated(EnumType.STRING)
    private TradeType type;

    // 거래량
    private Long volume;
    // 거래가
    private BigDecimal price;
    // 총 가격
    private BigDecimal totalPrice;

    // 거래 시간
    @CreatedDate
    @CreationTimestamp // INSERT 시 자동으로 값을 채워줌
    private LocalDateTime tradeAt;

    // 어떤 종목인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCK_ID")
    private Stock stock;
}
