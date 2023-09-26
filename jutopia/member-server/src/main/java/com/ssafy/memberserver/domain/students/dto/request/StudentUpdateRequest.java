package com.ssafy.memberserver.domain.students.dto.request;

public record StudentUpdateRequest(
        String StudentPwd,
        String StudentNewPwd
) {
}
