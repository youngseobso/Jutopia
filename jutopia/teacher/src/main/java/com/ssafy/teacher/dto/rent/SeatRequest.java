package com.ssafy.teacher.dto.rent;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SeatRequest {
    private String school;
    private int totalCount;
    private int grade;
    private int clazzNumber;
}
