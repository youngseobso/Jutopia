package com.ssafy.memberserver.domain.pointtransaction.repository;

import com.ssafy.memberserver.domain.pointtransaction.entity.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTransactionRepository extends JpaRepository<PointTransaction,Long> {

}
