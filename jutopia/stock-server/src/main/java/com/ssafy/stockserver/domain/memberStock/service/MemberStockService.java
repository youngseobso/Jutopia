package com.ssafy.stockserver.domain.memberStock.service;


import com.ssafy.stockserver.domain.memberStock.entity.MemberStock;
import com.ssafy.stockserver.domain.stock.entity.Stock;
import com.ssafy.stockserver.domain.trading.entity.Trading;
import com.ssafy.stockserver.domain.trading.vo.request.RequestTrade;

import java.util.Optional;
import java.util.UUID;

public interface MemberStockService {
    Iterable<MemberStock> getMemberStocks(UUID memberId);

    // 매수/매도에 따라 회원 보유 종목 업데이트
    void updateStock(Trading trade);

    Optional<MemberStock> getMemberOneStock(UUID memberId, UUID stockId);
}
