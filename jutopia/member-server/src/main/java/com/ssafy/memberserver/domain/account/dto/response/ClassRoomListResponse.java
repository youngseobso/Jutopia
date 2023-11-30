package com.ssafy.memberserver.domain.account.dto.response;

import com.ssafy.memberserver.domain.students.entity.Student;
import lombok.Builder;

@Builder
public record ClassRoomListResponse(
        String studentName,
        Integer studentNumber
) {
    public static ClassRoomListResponse from(Student student){
        return ClassRoomListResponse.builder()
                .studentName(student.getStudentName())
                .studentNumber(student.getStudentNumber())
                .build();
    }
}
