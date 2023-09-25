package com.ssafy.rentserver.dto;

import com.ssafy.rentserver.enums.SeatStatus;

import java.math.BigDecimal;

public record SeatRequest(
        BigDecimal price,
        SeatStatus seatStatus
) {

}
