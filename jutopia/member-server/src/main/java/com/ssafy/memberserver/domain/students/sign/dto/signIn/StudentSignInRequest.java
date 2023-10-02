package com.ssafy.memberserver.domain.students.sign.dto.signIn;

public record StudentSignInRequest(
        String StudentId,
        String StudentPwd
) {
}
