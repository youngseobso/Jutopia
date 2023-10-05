package com.ssafy.memberserver.domain.students.dto.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class StudentPointUpdateRequest {
    private String studentId;
    private String seatId;
    private BigDecimal point;
}
