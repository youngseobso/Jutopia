package com.ssafy.memberserver.domain.teachers.entity;

import com.ssafy.memberserver.common.enums.MemberBioStatus;
import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.common.enums.MemberStatus;
import com.ssafy.memberserver.domain.teachers.dto.request.TeacherDeleteRequest;
import com.ssafy.memberserver.domain.teachers.dto.request.TeacherUpdateRequest;
import com.ssafy.memberserver.domain.teachers.sign.dto.SignUp.request.TeacherSignUpRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Teacher {
    //TODO: validation 필요
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String teacherId;
    private String teacherPwd;
    private String teacherName;
    private String teacherEmail;
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;
    private String school;
    private int grade;
    private int classroom;
    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;
    @Enumerated(EnumType.STRING)
    private MemberBioStatus memberBioStatus;

    public static Teacher from(TeacherSignUpRequest teacherSignUpRequest, PasswordEncoder passwordEncoder) {
        return Teacher.builder()
                .teacherId(teacherSignUpRequest.getTeacherId())
                .teacherPwd(passwordEncoder.encode(teacherSignUpRequest.getTeacherPwd()))
                .teacherName(teacherSignUpRequest.getTeacherName())
                .teacherEmail(teacherSignUpRequest.getTeacherEmail())
                .memberRole(MemberRole.TEACHER)
                .memberStatus(MemberStatus.ACTIVE)
                .memberBioStatus(MemberBioStatus.INACTIVE)
                .school(teacherSignUpRequest.getSchool())
                .grade(teacherSignUpRequest.getGrade())
                .classroom(teacherSignUpRequest.getClassroom())
                .build();
    }
    //TODO:삼항 연사자 변경 필요
    public void update(TeacherUpdateRequest teacherUpdateRequest,PasswordEncoder passwordEncoder){
        if(teacherUpdateRequest.getTeacherNewPwd() != null || !teacherUpdateRequest.getTeacherPwd().isBlank()){
            this.teacherPwd = passwordEncoder.encode(teacherUpdateRequest.getTeacherNewPwd());
        }
    }
    public void delete(TeacherDeleteRequest teacherDeleteRequest){
        if(teacherDeleteRequest.getMemberStatus() == MemberStatus.ACTIVE){
            this.memberStatus = MemberStatus.INACTIVE;
        }
    }
}
