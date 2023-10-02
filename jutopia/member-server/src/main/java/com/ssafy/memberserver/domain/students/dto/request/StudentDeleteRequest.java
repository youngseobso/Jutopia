package com.ssafy.memberserver.domain.students.dto.request;

import com.ssafy.memberserver.common.enums.MemberStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record StudentDeleteRequest(
        @Schema(description = "학생 아이디")
        String studentId,
        @Schema(description = "학생 비밀 번호")
        String studentPwd,
        @Schema(description = "학생 탈퇴 여부")
        MemberStatus memberStatus
) {
}
