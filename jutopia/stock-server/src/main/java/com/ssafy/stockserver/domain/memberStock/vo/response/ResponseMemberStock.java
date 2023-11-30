package com.ssafy.stockserver.domain.memberStock.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.stockserver.domain.stock.entity.Stock;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMemberStock {
    private UUID id;

    private UUID stockId;
    private String stockName;
    private String stockCode;
    private BigDecimal nowMoney;
    private BigDecimal prevMoney;
    private BigDecimal changeMoney;
    private Double changeRate;
    // + / -
    private int type;

    /**
     * 보유 주식의 총 수량입니다.
     */
    private Long qty;
    /**
     * 보유 주식의 매입금액 입니다.
     */
    private BigDecimal totalPrice;
    /**
     * 보유 주식의 평단가격입니다.
     */
    private BigDecimal avgPrice;
}
