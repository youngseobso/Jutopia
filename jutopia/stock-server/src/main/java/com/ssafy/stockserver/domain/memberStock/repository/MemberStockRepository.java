package com.ssafy.stockserver.domain.memberStock.repository;

import com.ssafy.stockserver.domain.memberStock.entity.MemberStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberStockRepository extends JpaRepository<MemberStock, UUID> {
    Iterable<MemberStock> findAllByMemberId(UUID memberId);
}
