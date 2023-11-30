package com.ssafy.stockserver.domain.memberStock.entity;

import com.ssafy.stockserver.domain.stock.entity.Stock;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "memberstock")
@NoArgsConstructor
public class MemberStock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBERSTOCK_ID")
    private UUID id;

    // 누구의 거래 인지
    private UUID memberId;
    // 총 보유 수량
    private Long qty;
    // 총 매입금액
    private BigDecimal totalPrice;
    // 평단가
    private BigDecimal avgPrice;

    @CreatedDate
    @CreationTimestamp // INSERT 시 자동으로 값을 채워줌
    private LocalDateTime tradeAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCK_ID")
    private Stock stock;

    public MemberStock update(Long qty, BigDecimal totalPrice, BigDecimal avgPrice) {
        this.qty = qty;
        this.totalPrice = totalPrice;
        this.avgPrice = avgPrice;
        return this;
    }

    // 생성자에 @Builder 적용
    @Builder
    public MemberStock(UUID memberId, Long qty, BigDecimal totalPrice, BigDecimal avgPrice, Stock stock) {
        this.memberId = memberId;
        this.qty = qty;
        this.totalPrice = totalPrice;
        this.avgPrice = avgPrice;
        this.stock = stock;
    }
}
