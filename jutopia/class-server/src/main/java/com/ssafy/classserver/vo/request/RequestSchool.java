package com.ssafy.classserver.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RequestSchool {
    @NotNull(message = "학교를 설정 해주세요")
    private String schoolName;

    @NotNull(message = "학교 코드를 설정 해주세요")
//    @UniqueElements(message = "동일한 학교가 존재 합니다")
    private String schoolCode;
    private String region;

    // 각 학년 정보를 담는 리스트
    private List<RequestGrade> grades;
}