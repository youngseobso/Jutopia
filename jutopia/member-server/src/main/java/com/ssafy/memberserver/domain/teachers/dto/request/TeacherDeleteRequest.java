package com.ssafy.memberserver.domain.teachers.dto.request;

import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.common.enums.MemberStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class TeacherDeleteRequest {
        @Schema(description = "선생님 아이디")
        private String teacherId;

        @Schema(description = "선생님 비밀번호")
        private String teacherPwd;

        @Schema(description = "선생님 이름")
        private String teacherName;

        @Schema(description = "Member의 권한")
        private MemberRole memberRole;

        @Schema(description = "Member의 탈퇴 여부")
        private MemberStatus memberStatus;
}
