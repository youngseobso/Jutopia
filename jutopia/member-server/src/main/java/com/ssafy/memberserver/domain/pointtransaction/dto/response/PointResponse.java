package com.ssafy.memberserver.domain.pointtransaction.dto.response;

import com.ssafy.memberserver.domain.students.entity.Student;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PointResponse(
        BigDecimal point
) {
    public static PointResponse from(Student student){
        return PointResponse.builder()
                .point(student.getPoint())
                .build();
    }
}
