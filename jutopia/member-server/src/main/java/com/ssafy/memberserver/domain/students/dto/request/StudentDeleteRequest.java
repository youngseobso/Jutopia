package com.ssafy.memberserver.domain.students.dto.request;

import com.ssafy.memberserver.common.enums.MemberStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class StudentDeleteRequest{
        @Schema(description = "학생 아이디")
        private String studentId;

        @Schema(description = "학생 비밀 번호")
        private String studentPwd;

        @Schema(description = "학생 탈퇴 여부")
        private MemberStatus memberStatus;
}
