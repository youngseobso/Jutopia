package com.ssafy.stockserver.domain.trading.repository;

import com.ssafy.stockserver.domain.stock.entity.Stock;
import com.ssafy.stockserver.domain.trading.entity.Trading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TradingRepository extends JpaRepository<Trading, UUID> {
}
