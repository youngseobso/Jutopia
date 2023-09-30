package com.ssafy.classserver.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestGrade {
    @NotNull(message = "학년을 설정 해주세요")
    private int gradeNum;

    // 학년별 gradeAccountPoint 및 gradeAccountInitPoint 설정
    private BigDecimal gradeAccountPoint;
    private BigDecimal gradeAccountInitPoint;
}