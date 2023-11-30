package com.ssafy.classserver.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseGrade {
    private UUID id;
    private int gradeNum;
    private BigDecimal gradeAccountPoint;
    private BigDecimal gradeAccountInitPoint;

    // 해당 학교의 등록된 학년들을 보여준다
    private List<ResponseClassRoom> classRooms;
}
