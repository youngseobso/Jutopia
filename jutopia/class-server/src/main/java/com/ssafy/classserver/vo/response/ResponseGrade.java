package com.ssafy.classserver.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseGrade {
    private UUID id;
    private int gradeNum;
    private BigDecimal gradeAccountPoint;
    private BigDecimal gradeAccountInitPoint;
}
