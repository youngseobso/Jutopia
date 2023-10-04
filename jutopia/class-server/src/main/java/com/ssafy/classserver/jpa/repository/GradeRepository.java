package com.ssafy.classserver.jpa.repository;

import com.ssafy.classserver.jpa.entity.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface GradeRepository extends JpaRepository<GradeEntity, UUID> {
    Iterable<GradeEntity> findAllBySchoolId(UUID schoolId);

}
