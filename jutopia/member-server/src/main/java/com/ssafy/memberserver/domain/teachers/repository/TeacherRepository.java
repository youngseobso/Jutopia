package com.ssafy.memberserver.domain.teachers.repository;

import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.domain.teachers.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    Optional<Teacher> findByTeacherId(String teacherId);
    Optional<Teacher> findByTeacherIdAndMemberRole(String memberId, MemberRole memberRole);
    Optional<Teacher> findByTeacherEmail(String teacherEmail);
    boolean existsByTeacherId(String teacherId);
}
