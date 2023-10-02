package com.ssafy.rentserver.dto;

import com.ssafy.rentserver.enums.SeatStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class SeatChangeRequest {
    private String seatId;
    private BigDecimal price;
    private String userId;
    private SeatStatus seatStatus;

}
