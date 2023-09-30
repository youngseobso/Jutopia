package com.ssafy.stockserver.domain.trading.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class KafkaTradeDto implements Serializable {
    private Schema schema;
    private Payload payload;
}
