package com.ssafy.memberserver.domain.history.repository;

import com.ssafy.memberserver.domain.history.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HistoryRepository extends JpaRepository<History,Long> {
    List<History> findByAccountId(UUID accountId);
}
