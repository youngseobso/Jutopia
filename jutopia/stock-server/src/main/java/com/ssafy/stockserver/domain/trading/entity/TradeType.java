package com.ssafy.stockserver.domain.trading.entity;

import lombok.Getter;

@Getter
public enum TradeType {
    BUY("매수"),
    SELL("매도");

    private String type;

    TradeType(String type) {
        this.type = type;
    }
}
