package com.ssafy.stockserver.domain.stock.repository;

import com.ssafy.stockserver.domain.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, UUID> {
}
