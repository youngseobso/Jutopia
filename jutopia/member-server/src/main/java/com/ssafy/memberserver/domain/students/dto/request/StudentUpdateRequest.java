package com.ssafy.memberserver.domain.students.dto.request;

import com.ssafy.memberserver.common.enums.MemberStatus;

public record StudentUpdateRequest(
        String studentId,
        String studentPwd,
        String studentNewPwd
) {
}
