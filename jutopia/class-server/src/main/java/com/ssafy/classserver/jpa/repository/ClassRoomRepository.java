package com.ssafy.classserver.jpa.repository;

import com.ssafy.classserver.jpa.entity.ClassRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClassRoomRepository extends JpaRepository<ClassRoomEntity, UUID> {
    List<ClassRoomEntity> findAll();
    Optional<ClassRoomEntity> findById(UUID id);

    Iterable<ClassRoomEntity> findAllByGradeId(UUID gradeId);

    Optional<ClassRoomEntity> findFirstByClassNumAndGradeId(int classNum, UUID gradeId);
}
