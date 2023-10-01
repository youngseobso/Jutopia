package com.ssafy.classserver.jpa.repository;

import com.ssafy.classserver.jpa.entity.MemberSavingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberSavingRepository extends JpaRepository<MemberSavingEntity, UUID> {
    Optional<MemberSavingEntity> findByMemberId(UUID memberId);
}
