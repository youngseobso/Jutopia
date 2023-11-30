package com.ssafy.memberserver.domain.students.sign.dto.signUp;

import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.common.enums.MemberStatus;
import com.ssafy.memberserver.domain.students.entity.Student;
import lombok.Builder;

@Builder
public record StudentSignUpResponse(
        MemberRole memberRole,
        MemberStatus memberStatus
) {
    public static StudentSignUpResponse from(Student student){
        return StudentSignUpResponse.builder()
                .memberRole(student.getMemberRole())
                .memberStatus(student.getMemberStatus())
                .build();
    }
}
