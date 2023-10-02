package com.ssafy.memberserver.domain.students.sign.dto.signIn;

import com.ssafy.memberserver.common.enums.MemberRole;
import lombok.Getter;

@Getter
public class StudentSignInRequest {
    private String studentId;
    private String studentPwd;
    private MemberRole memberRole;
}
