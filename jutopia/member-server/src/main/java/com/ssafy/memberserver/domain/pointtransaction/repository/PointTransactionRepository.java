package com.ssafy.memberserver.domain.pointtransaction.repository;

import com.ssafy.memberserver.domain.pointtransaction.entity.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PointTransactionRepository extends JpaRepository<PointTransaction,Long> {
    List<PointTransaction> findByStudentId(UUID id);
}
