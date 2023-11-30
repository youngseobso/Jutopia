package com.ssafy.memberserver.domain.notice.repository;

import com.ssafy.memberserver.domain.notice.entity.Notice;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE notice SET view_count = view_count + 1 WHERE id = :id", nativeQuery = true)
    int updateViewCount(@Param("id") Long id);
    @Query(value = "SELECT n FROM Notice n WHERE n.school = :school AND n.grade = :grade AND n.classroom = :classroom ORDER BY n.id DESC, n.createdAt DESC")
    List<Notice> findBySchoolAndGradeAndClassroom(@Param("school") String school, @Param("grade") int grade, @Param("classroom") int classroom);
}
