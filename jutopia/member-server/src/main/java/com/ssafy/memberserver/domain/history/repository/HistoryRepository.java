package com.ssafy.memberserver.domain.history.repository;

import com.ssafy.memberserver.common.enums.HistoryType;
import com.ssafy.memberserver.domain.history.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface HistoryRepository extends JpaRepository<History,Long> {
    List<History> findByAccountId(UUID accountId);
    List<History> findByAccountIdAndHistoryType(UUID accountId, HistoryType historyType);
    List<History> findByAccount_IdAndHistoryTypeAndCreatedAtBetween(UUID accountId, HistoryType historyType, LocalDateTime startDate, LocalDateTime endDate);
}
