package com.ssafy.classserver.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSchool {
    private UUID id;
    private String schoolName;
    private String schoolCode;
    private String region;
    // 해당 학교의 등록된 학년들을 보여준다
    private List<ResponseGrade> grades;
}
