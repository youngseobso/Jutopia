package com.ssafy.stockserver.domain.trading.service;

import com.ssafy.stockserver.domain.trading.entity.Trading;
import com.ssafy.stockserver.domain.trading.repository.TradingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TradingServiceImpl implements TradingService{

    TradingRepository tradingRepository;

    @Autowired
    public TradingServiceImpl(TradingRepository tradingRepository) {
        this.tradingRepository = tradingRepository;
    }

    @Override
    public Trading save(Trading trade) {
        return tradingRepository.save(trade);
    }
}
