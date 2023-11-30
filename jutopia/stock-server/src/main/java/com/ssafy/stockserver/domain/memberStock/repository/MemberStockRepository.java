package com.ssafy.stockserver.domain.memberStock.repository;

import com.ssafy.stockserver.domain.memberStock.entity.MemberStock;
import com.ssafy.stockserver.domain.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberStockRepository extends JpaRepository<MemberStock, UUID> {
    Iterable<MemberStock> findAllByMemberId(UUID memberId);

    Optional<MemberStock> findByMemberIdAndStockId(UUID memberId, UUID stockId);

}
