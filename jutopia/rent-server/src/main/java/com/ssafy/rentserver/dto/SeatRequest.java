package com.ssafy.rentserver.dto;

import com.ssafy.rentserver.enums.SeatStatus;

import java.math.BigDecimal;

public record SeatRequest(
        String seatId,
        String userId,
        BigDecimal price,
        SeatStatus seatStatus
) {

}
