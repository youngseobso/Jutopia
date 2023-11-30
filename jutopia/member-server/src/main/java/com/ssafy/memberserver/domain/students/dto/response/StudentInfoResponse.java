package com.ssafy.memberserver.domain.students.dto.response;

import com.ssafy.memberserver.common.enums.MemberBioStatus;
import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.common.enums.MemberStatus;
import com.ssafy.memberserver.common.enums.SeatOwnershipStatus;
import com.ssafy.memberserver.domain.students.entity.Student;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record StudentInfoResponse(
        @Schema(description = "학생 고유키")
        UUID id,
        @Schema(description = "학생 아이디")
        String studentId,
        @Schema(description = "학생 이름")
        String studentName,
        @Schema(description = "학생 포인트")
        BigDecimal point,
        @Schema(description = "학생 화폐 총량")
        BigDecimal money,
        @Schema(description = "학생의 학교")
        String school,
        @Schema(description = "학생의 학년")
        int grade,
        @Schema(description = "학생의 반")
        int classRoom,
        @Schema(description = "학생 권한")
        MemberRole memberRole,
        @Schema(description = "학생 상태 여부")
        MemberStatus memberStatus,
        @Schema(description = "학생 바이오 여부")
        MemberBioStatus memberBioStatus,
        @Schema(description = "학생 좌석 보유 여부")
        SeatOwnershipStatus seatOwnershipStatus,
        @Schema(description = "학생 생성일")
        LocalDateTime createdAt,
        @Schema(description = "학생 수정일")
        LocalDateTime updateAt
) {
        public static StudentInfoResponse from(Student student){
                return StudentInfoResponse.builder()
                        .id(student.getId())
                        .studentId(student.getStudentId())
                        .studentName(student.getStudentName())
                        .point(student.getPoint())
                        .money(student.getMoney())
                        .school(student.getSchool())
                        .grade(student.getGrade())
                        .classRoom(student.getClassRoom())
                        .memberRole(student.getMemberRole())
                        .memberStatus(student.getMemberStatus())
                        .memberBioStatus(student.getMemberBioStatus())
                        .seatOwnershipStatus(student.getSeatOwnershipStatus())
                        .createdAt(student.getCreatedAt())
                        .updateAt(student.getUpdatedAt())
                        .build();
        }
}
