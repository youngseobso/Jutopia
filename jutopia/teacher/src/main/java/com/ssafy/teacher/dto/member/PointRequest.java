package com.ssafy.teacher.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
public class PointRequest {
    private String school;
    private int grade;
    private int classroom;
    private BigDecimal income;
}
