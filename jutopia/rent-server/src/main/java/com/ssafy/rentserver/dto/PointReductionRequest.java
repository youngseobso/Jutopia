package com.ssafy.rentserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class PointReductionRequest {
    String studentId;
    String seatId;
    BigDecimal point;
}
