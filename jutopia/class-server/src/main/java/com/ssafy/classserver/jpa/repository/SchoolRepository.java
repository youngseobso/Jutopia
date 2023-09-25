package com.ssafy.classserver.jpa.repository;

import com.ssafy.classserver.jpa.entity.GradeEntity;
import com.ssafy.classserver.jpa.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SchoolRepository extends JpaRepository<SchoolEntity, UUID> {
}
