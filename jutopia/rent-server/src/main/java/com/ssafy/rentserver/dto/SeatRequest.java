package com.ssafy.rentserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatRequest {

    private String userId;
    private String seatId;
}